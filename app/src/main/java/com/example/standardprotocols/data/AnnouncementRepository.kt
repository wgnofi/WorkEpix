package com.example.standardprotocols.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed interface AnnouncementResult {
    data object Success: AnnouncementResult
    data object Error: AnnouncementResult
}

sealed interface AnnouncementListResult {
    data class Success(val list: List<Announcement>): AnnouncementListResult
    data object Idle: AnnouncementListResult
    data object Error: AnnouncementListResult
}

interface AnnouncementRepository {
    suspend fun addAnnouncement(a: Announcement): Flow<AnnouncementResult>
    suspend fun getLiveAnnouncementForUser(managerId: String): Flow<AnnouncementListResult>
    suspend fun getLiveAnnouncementForManager(uid: String): Flow<AnnouncementListResult>
}

class FirebaseAnnouncementRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): AnnouncementRepository {
    private val announcementCollection = firestore.collection("announcements")
    override suspend fun addAnnouncement(a: Announcement): Flow<AnnouncementResult> = flow {
        val docRef = announcementCollection.add(a.toHashMap()).await()
        val snapshot = docRef.get().await()
        val createdAnnouncement =
            snapshot.toObject(Announcement::class.java)?.copy(id = snapshot.id)
        if (createdAnnouncement != null) {
            emit(AnnouncementResult.Success)
        } else {
            emit(AnnouncementResult.Error)
        }
    }

    override suspend fun getLiveAnnouncementForUser(managerId: String): Flow<AnnouncementListResult> = callbackFlow {
        val query = announcementCollection.
                whereEqualTo("uid", managerId)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("ANNOUNCEMENT", "Error fetching active active Announcement: ${error.message}", error)
                trySend(AnnouncementListResult.Error).isSuccess
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val announcements = snapshot.documents.mapNotNull { document ->
                    document.toObject(Announcement::class.java)?.copy(id = document.id)
                }
                trySend(AnnouncementListResult.Success(announcements)).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }

    override suspend fun getLiveAnnouncementForManager(uid: String): Flow<AnnouncementListResult> = callbackFlow {
        val query = announcementCollection
            .whereEqualTo("uid", uid)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("ANNOUNCEMENT", "Error fetching active active Announcement: ${error.message}", error)
                trySend(AnnouncementListResult.Error).isSuccess
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val announcements = snapshot.documents.mapNotNull { document ->
                    document.toObject(Announcement::class.java)?.copy(id = document.id)
                }
                trySend(AnnouncementListResult.Success(announcements)).isSuccess
            }
        }
        awaitClose { listener.remove() }
    }
}

fun Announcement.toHashMap(): HashMap<String?, String?>
{
    return hashMapOf(
        "uid" to uid,
        "postContent" to postContent
    )
}

