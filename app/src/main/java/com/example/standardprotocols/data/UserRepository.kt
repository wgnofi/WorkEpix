package com.example.standardprotocols.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable? = null, val message: String? = null) : Result<Nothing>()
}
interface UserRepository {
    suspend fun createUserProfile(uid: String, displayName: String?, email: String?, role: String?, managerId: String?): Result<Unit>
    suspend fun checkUserExists(uidToCheck: String): Boolean
    suspend fun getUserByID(id: String): User?
    suspend fun getNameByID(id: String): String
}

class FirebaseUserRepository @Inject constructor(private val firestore: FirebaseFirestore) : UserRepository {
    private val userCollection = firestore.collection("users")
    override suspend fun createUserProfile(
        uid: String,
        displayName: String?,
        email: String?,
        role: String?,
        managerId: String?
    ): Result<Unit> {
        return try {
            val user = User(uid, displayName, email, role, managerId)
            Log.d("userCollection define", "Look")
            userCollection.document(uid).set(user).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.d("failed", "Look")
            Result.Error(e)
        }
    }
    override suspend fun checkUserExists(uidToCheck: String): Boolean {
        return try {
            val document = userCollection.document(uidToCheck).get().await()
            document.exists()
        } catch (e: Exception) {
            println("Error checking user existence: $e")
            return false
        }
    }

    override suspend fun getUserByID(id: String): User? {
        val doc = userCollection.document(id).get().await()
        return convertToUser(doc.data)
    }

    override suspend fun getNameByID(id: String): String {
        val nameDoc = userCollection.document(id).get().await()
        return nameDoc["displayName"] as? String ?: "No Manager"
    }


}
fun convertToUser(m: MutableMap<String, Any>?): User? {
    return try {
        if (m == null) return null

        User(
            uid = m["uid"] as String,
            displayName = m["displayName"] as? String,
            email = m["email"] as? String,
            role = m["role"] as? String,
            managerId = m["managerId"] as? String,
        )
    } catch (e: ClassCastException) {
        Log.e("Convert", "Error casting value during user conversion: ${e.message}")
        null
    } catch (e: Exception) {
        Log.e("Convert", "Unexpected error during user conversion: ${e.message}")
        null
    }
}