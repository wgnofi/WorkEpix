package com.example.standardprotocols.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.standardprotocols.data.AuthRepository
import com.example.standardprotocols.data.AuthResult
import com.example.standardprotocols.data.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Idle : LoginState()
    data object LoggingIn : LoginState()
    data class Success(val user: User) : LoginState()
    data class Failure(val errorMessage: String) : LoginState()
    data object AlreadyLoggedIn : LoginState()
    data object LoggedIn : LoginState()
    data object LoggedOut : LoginState()
    data object Registering : LoginState()
    data class RegistrationFailure(val errorMessage: String) : LoginState()
    data object LoggingOut : LoginState()
    data class SignOutFailure(val errorMessage: String) : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    init {
        checkIfUserIsLoggedIn()
    }
    private fun checkIfUserIsLoggedIn() {
        if (auth.currentUser != null) {
            _loginState.value = LoginState.LoggedIn
        }
    }


    val user: StateFlow<User?> = authRepository.getCurrentUser().map { res ->
            when(res) {
                is AuthResult.Success -> res.data
                is AuthResult.Error -> null
                AuthResult.Loading -> null
                AuthResult.UnAuthorized -> null
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.loginWithEmailAndPassword(email, password)
                .onStart { _loginState.value = LoginState.LoggingIn }
                .map { authResult ->
                    when (authResult) {
                        is AuthResult.Success -> LoginState.Success(authResult.data)
                        is AuthResult.Error -> LoginState.Failure(authResult.exception.localizedMessage ?: "Login failed")
                        AuthResult.Loading -> LoginState.LoggingIn
                        AuthResult.UnAuthorized -> LoginState.Failure( "Login failed unauthorized")
                    }
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = LoginState.Idle
                )
                .collectLatest { _loginState.value = it }
        }
    }


//    fun createUser(email: String, password: String) {
//        viewModelScope.launch {
//            authRepository.signUpWithEmailAndPassword(email, password)
//                .onStart { _loginState.value = LoginState.Registering }
//                .map { authResult ->
//                    when (authResult) {
//                        is AuthResult.Success -> LoginState.Success(authResult.data)
//                        is AuthResult.Error -> LoginState.RegistrationFailure(authResult.exception.localizedMessage ?: "Registration failed")
//                        AuthResult.Loading -> LoginState.Registering
//                        AuthResult.Loading -> LoginState.LoggingIn
//                        AuthResult.UnAuthorized -> LoginState.Failure( "Login failed unauthorized")
//                    }
//                }
//                .stateIn(
//                    scope = viewModelScope,
//                    started = SharingStarted.WhileSubscribed(5000),
//                    initialValue = LoginState.Idle
//                )
//                .collectLatest { _loginState.value = it }
//        }
//    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
                .onStart { _loginState.value = LoginState.LoggingOut }
                .map { authResult ->
                    when (authResult) {
                        is AuthResult.Success -> LoginState.LoggedOut
                        is AuthResult.Error -> LoginState.SignOutFailure(authResult.exception.localizedMessage ?: "Sign out failed")
                        AuthResult.Loading -> LoginState.LoggingOut
                        AuthResult.Loading -> LoginState.LoggingIn
                        AuthResult.UnAuthorized -> LoginState.Failure( "Login failed unauthorized")
                    }
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = LoginState.LoggedIn
                )
                .collectLatest { _loginState.value = it }
        }
    }
}