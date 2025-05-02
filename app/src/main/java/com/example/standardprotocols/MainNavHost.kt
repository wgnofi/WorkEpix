package com.example.standardprotocols

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardprotocols.navigation.AnnouncementNavHost
import com.example.standardprotocols.navigation.HomeNavHost
import com.example.standardprotocols.navigation.ManagerNavHost
import com.example.standardprotocols.ui.screens.home.employee.IssueViewModel
import com.example.standardprotocols.ui.screens.home.employee.LeaveViewModel
import com.example.standardprotocols.ui.screens.login.LoginViewModel
import com.example.standardprotocols.ui.screens.login.UserViewModel

sealed class MainNav(val route: String) {
    data object Home : MainNav("home") {
        override val icon: ImageVector = Icons.Default.Home
    }
    data object Announcement : MainNav("announcement") {
        override val icon: ImageVector = Icons.Default.Notifications
    }
    abstract val icon: ImageVector?
}
@Composable
fun MainNavHost(
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel,
    leaveViewModel: LeaveViewModel = hiltViewModel(),
    issueViewModel: IssueViewModel = hiltViewModel(),
    onLogOut: () -> Unit) {
    val user by loginViewModel.user.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val screens = listOf(MainNav.Home, MainNav.Announcement)
    var currentScreen by rememberSaveable {
        mutableStateOf(navController.currentDestination?.route ?: "login")
    }
    Scaffold(
        topBar = { Text("") },
        bottomBar = {
                NavigationBar {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            selected = currentScreen == screen.route,
                            onClick = {
                                navController.navigate(screen.route)
                                currentScreen = screen.route
                            },
                            icon = {
                                if (screen.icon != null) {
                                    Icon(imageVector = screen.icon!!, contentDescription = null)
                                }
                            }
                        )
                    }
                }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = MainNav.Home.route, modifier = Modifier.padding(padding) ) {
            composable(route = MainNav.Home.route) {
                when(user?.role) {
                    "Manager" -> {
                        ManagerNavHost(onLogOut,loginViewModel, leaveViewModel, issueViewModel)
                    }
                    null -> {
                        Box(modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)) {
                            Text("Loading...")
                        }
                    }
                    else -> {
                        HomeNavHost(user, onLogOut = onLogOut, userViewModel = userViewModel, leaveViewModel = leaveViewModel,
                            issueViewModel = issueViewModel)
                    }
                }
            }
            composable(route = MainNav.Announcement.route) {
                AnnouncementNavHost(user = user)
            }
        }
    }
}