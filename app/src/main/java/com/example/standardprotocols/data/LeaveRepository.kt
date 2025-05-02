package com.example.standardprotocols.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Result


sealed interface LeaveResult {
    data object Loading: LeaveResult
    data class Success(val leave: Leave): LeaveResult
    data class Error(val e: String): LeaveResult
}

sealed interface LeaveListResult {
    data object Loading: LeaveListResult
    data class Success(val leaveList: List<Leave>): LeaveListResult
    data class Error(val e: String): LeaveListResult
}

interface LeaveRepository {
    suspend fun applyLeave(leave: Leave): Flow<LeaveResult>
    suspend fun getLeaveListForUserOnce(id: String): Flow<LeaveListResult>
    suspend fun getActivePendingLeaves(id: String): Flow<LeaveListResult>
    suspend fun approveLeave(leaveId: String): Flow<Result<Unit>>
    suspend fun rejectLeave(leaveId: String): Flow<Result<Unit>>

    suspend fun getApprovedLeavesForUserOnce(id: String): Flow<LeaveListResult>
    suspend fun getRejectedLeavesForUserOnce(id: String): Flow<LeaveListResult>
}

class FirebaseLeaveRepository @Inject
constructor(
    private val firestore: FirebaseFirestore
): LeaveRepository {

    private val leaveCollection = firestore.collection("leaves")


    override suspend fun applyLeave(leave: Leave): Flow<LeaveResult> = flow {
        emit(LeaveResult.Loading)
        try {
            val docRef = leaveCollection.add(leave.toHashMap()).await()
            val snapshot = docRef.get().await()
            val createdLeave = snapshot.toObject(Leave::class.java)?.copy(id = snapshot.id)
            if (createdLeave != null) {
                emit(LeaveResult.Success(createdLeave))
            } else {
                emit(LeaveResult.Error("Failed to retrieve the created leave application."))
            }
        } catch (e: Exception) {
            emit(LeaveResult.Error("Failed to apply for leave"))
        }
    }



    override suspend fun getLeaveListForUserOnce(id: String): Flow<LeaveListResult> = flow {
        emit(LeaveListResult.Loading)
        try {
            val snapshot = leaveCollection.whereEqualTo("userId", id).get().await()
            val leaves = snapshot.documents.mapNotNull { document ->
                document.toObject(Leave::class.java)?.copy(id = document.id)
            }
            emit(LeaveListResult.Success(leaves))
        } catch (e: Exception) {
            emit(LeaveListResult.Error("Failed to fetch leaves for current user"))
        }
    }

    override suspend fun getApprovedLeavesForUserOnce(id: String): Flow<LeaveListResult> = flow {
        emit(LeaveListResult.Loading)
        try {
            val snapshot = leaveCollection.whereEqualTo("managerId", id)
                .whereEqualTo("status", "Approved")
                .get()
                .await()
            val leaves = snapshot.documents.mapNotNull { document ->
                document.toObject(Leave::class.java)?.copy(id = document.id)
            }
            emit(LeaveListResult.Success(leaves))
        } catch (e: Exception) {
            emit(LeaveListResult.Error("Failed to fetch approved leaves for current user"))
        }
    }

    override suspend fun getRejectedLeavesForUserOnce(id: String): Flow<LeaveListResult> = flow {
        emit(LeaveListResult.Loading)
        try {
            val snapshot = leaveCollection.whereEqualTo("managerId", id)
                .whereEqualTo("status", "Rejected")
                .get()
                .await()
            val leaves = snapshot.documents.mapNotNull { document ->
                document.toObject(Leave::class.java)?.copy(id = document.id)
            }
            emit(LeaveListResult.Success(leaves))
        } catch (e: Exception) {
            emit(LeaveListResult.Error("Failed to fetch rejected leaves for current user"))
        }
    }


    override suspend fun getActivePendingLeaves(id: String): Flow<LeaveListResult> = callbackFlow {
        val query = leaveCollection
            .whereEqualTo("managerId", id)
            .whereEqualTo("status", "Pending")

        val listenerRegistration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("LEAVE", "Error fetching active pending leaves: ${error.message}", error)
                trySend(LeaveListResult.Error("Failed to fetch active pending leaves.")).isSuccess
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val leaves = snapshot.documents.mapNotNull { document ->
                    document.toObject(Leave::class.java)?.copy(id = document.id)
                }
                trySend(LeaveListResult.Success(leaves)).isSuccess
            }
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun approveLeave(leaveId: String): Flow<Result<Unit>> = flow {
        try {
            leaveCollection.document(leaveId)
                .update("status", "Approved")
                .await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

    }

    override suspend fun rejectLeave(leaveId: String): Flow<Result<Unit>> = flow {
        try {
            leaveCollection.document(leaveId)
                .update("status", "Rejected")
                .await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

fun Leave.toHashMap(): HashMap<String, String?> {
    return hashMapOf(
        "userId" to userId,
        "date" to date,
        "reason" to reason,
        "appliedDate" to appliedDate,
        "managerId" to managerId,
        "status" to status
    )
}