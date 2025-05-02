package com.example.standardprotocols.navigation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardprotocols.MainNavHost
import com.example.standardprotocols.ui.screens.login.LoginScreen
import com.example.standardprotocols.ui.screens.login.LoginState
import com.example.standardprotocols.ui.screens.login.LoginViewModel
import com.example.standardprotocols.ui.screens.login.UserViewModel

enum class LoginNav {
    Login, LoggedIn
}

@Composable
fun LoginNavHost(
    viewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val loginNavController = rememberNavController()
    NavHost(loginNavController, startDestination = LoginNav.Login.name) {
        composable(route = LoginNav.Login.name) {
            LoginScreen(loginNavController, viewModel, onLogIn = { email, password ->
                viewModel.login(email, password)
            })
        }
        composable(route = LoginNav.LoggedIn.name) {
            when (loginState) {
                is LoginState.Success, is LoginState.LoggedIn, is LoginState.AlreadyLoggedIn ->
                MainNavHost(loginViewModel = viewModel, onLogOut = {
                    viewModel.signOut()
                    loginNavController.popBackStack(route = LoginNav.Login.name, inclusive = false)
                }, userViewModel = userViewModel)
                else -> Box (modifier =
                    Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)) {
                    Text("Loading...", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}