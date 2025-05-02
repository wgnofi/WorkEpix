package com.example.standardprotocols.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed interface IssueResult {
    data object Loading : IssueResult
    data class Success(val issue: Issue) : IssueResult
    data class Error(val e: String) : IssueResult
}

sealed interface IssueListResult {
    data object Loading : IssueListResult
    data class Success(val issueList: List<Issue>) : IssueListResult
    data class Error(val e: String) : IssueListResult
}

interface IssueRepository {
    suspend fun submitIssue(issue: Issue): Flow<IssueResult>
    suspend fun getIssueListForUserOnce(id: String): Flow<IssueListResult>
}


class FirebaseIssueRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : IssueRepository {

    private val issueCollection = firestore.collection("issues")

    override suspend fun submitIssue(issue: Issue): Flow<IssueResult> = flow {
        emit(IssueResult.Loading)
        try {
            val docRef = issueCollection.add(issue.toHashMap()).await()
            val snapshot = docRef.get().await()
            val createdIssue = snapshot.toObject(Issue::class.java)?.copy(id = snapshot.id)
            if (createdIssue != null) {
                emit(IssueResult.Success(createdIssue))
            } else {
                emit(IssueResult.Error("Failed to submit the issue."))
            }
        } catch (e: Exception) {
            emit(IssueResult.Error(e.localizedMessage ?: "Failed to submit the issue."))
        }
    }

    override suspend fun getIssueListForUserOnce(id: String): Flow<IssueListResult> = flow {
        emit(IssueListResult.Loading)
        try {
            val snapshot = issueCollection.whereEqualTo("userId", id).get().await()
            val issues = snapshot.documents.mapNotNull { document ->
                document.toObject(Issue::class.java)?.copy(id = document.id)
            }
            emit(IssueListResult.Success(issues))
        } catch (e: Exception) {
            emit(IssueListResult.Error("Failed to fetch issues for current user."))
        }
    }
}
fun Issue.toHashMap(): HashMap<String, String?> {
    return hashMapOf(
        "userId" to userId,
        "title" to title,
        "description" to description,
        "date" to date,
        "managerId" to managerId,
        "status" to status
    )
}