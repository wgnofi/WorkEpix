package com.example.standardprotocols.data


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

const val TAG = "LOGIN"


sealed class AuthResult<out T> {
    data object Loading : AuthResult<Nothing>()
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()
    data object UnAuthorized: AuthResult<Nothing>()
}

interface AuthRepository {
    suspend fun loginWithEmailAndPassword(email:String, password:String): Flow<AuthResult<User>>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Flow<AuthResult<User>>
    fun getCurrentUser(): Flow<AuthResult<User?>>
    suspend fun signOut(): Flow<AuthResult<Unit>>
}

class FirebaseAuthRepository
@Inject constructor(private val auth: FirebaseAuth,
    private val userRepository: UserRepository): AuthRepository {
    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading)
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val user = result.user
        if (user != null) {
            emit(AuthResult.Success(user.toUser()))
        } else {
            emit(AuthResult.Error(Exception("Login okay!, but user data is null")))
        }
    }.catch { e ->
        Log.e(TAG, "loginWithEmailAndPassword:failure", e)
        emit(AuthResult.Error(Exception(e)))
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<AuthResult<User>> = flow {
        emit(AuthResult.Loading)
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        if (user != null) {
            if (userRepository.checkUserExists(user.uid)) {
                emit(AuthResult.Success(user.toUser()))
            }
        } else {
            emit(AuthResult.Error(Exception("Sign up okay!, but user data is null")))
        }
    }.catch { e ->
        Log.e(TAG, "SignUpWithEmailAndPassword:failure", e)
        emit(AuthResult.Error(Exception(e)))
    }
    override fun getCurrentUser(): Flow<AuthResult<User?>> = flow {
        emit(AuthResult.Loading)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val user = userRepository.getUserByID(currentUser.uid)
            emit(AuthResult.Success(user))
        } else {
            emit(AuthResult.UnAuthorized)
        }
    }.catch { e ->
        emit(AuthResult.Error(Exception(e)))
    }

    override suspend fun signOut(): Flow<AuthResult<Unit>> = flow {
        emit(AuthResult.Loading)
        auth.signOut()
        emit(AuthResult.Success(Unit))
    }.catch { e ->
        Log.e(TAG, "signOut:failure", e)
        emit(AuthResult.Error(Exception(e)))
    }
}

fun FirebaseUser.toUser():User {
    return User(
        uid = uid,
        email = email,
        displayName = email.toString().split("@")[0].replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        },
        role = "Employee",
        managerId = null
    )
}