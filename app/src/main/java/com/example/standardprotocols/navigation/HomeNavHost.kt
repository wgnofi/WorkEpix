package com.example.standardprotocols.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardprotocols.data.Issue
import com.example.standardprotocols.data.Leave
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.screens.home.employee.HealthPolicyScreen
import com.example.standardprotocols.ui.screens.home.employee.IssueScreen
import com.example.standardprotocols.ui.screens.home.employee.ProfileScreen
import com.example.standardprotocols.ui.screens.home.employee.IssueViewModel
import com.example.standardprotocols.ui.screens.home.employee.LeaveViewModel
import com.example.standardprotocols.ui.screens.home.employee.OvertimePolicyScreen
import com.example.standardprotocols.ui.screens.home.employee.ProtocolScreen
import com.example.standardprotocols.ui.screens.home.employee.RequestLeaveScreen
import com.example.standardprotocols.ui.screens.login.UserViewModel

object HomeDestinations {
    const val Home = "HomePage"
    const val Profile = "ProfilePage"
    const val Issue = "IssuePage"
    const val Leave = "LeavePage"
    const val Health = "HealthPage"
    const val Overtime = "OvertimePage"
}

@Composable
fun HomeNavHost(
    user: User?,
    userViewModel: UserViewModel,
    onLogOut: () -> Unit,
    leaveViewModel: LeaveViewModel,
    issueViewModel: IssueViewModel
) {
    LaunchedEffect(user) {
        if (user != null) {
            userViewModel.getManagerName(user.managerId ?: "")
        }
    }
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = HomeDestinations.Home,
            modifier = Modifier.padding(innerPadding)) {
            composable(route = HomeDestinations.Home) {
                ProtocolScreen(
                    user = user,
                    onProfileClick = {
                        navController.navigate(HomeDestinations.Profile)
                    },
                    onLeaveClick = { navController.navigate(HomeDestinations.Leave) },
                    onIssueClick = {
                        navController.navigate(HomeDestinations.Issue)
                    }, healthOnClick = {
                        navController.navigate(HomeDestinations.Health)
                    }, overtimeOnClick = {
                        navController.navigate(HomeDestinations.Overtime)
                    })
            }
            composable(route = HomeDestinations.Profile) {
                ProfileScreen(onNavBack = {navController.popBackStack()}, onLogOut =onLogOut, user = user,
                    userViewModel, leaveViewModel, issueViewModel)
            }
            composable(route = HomeDestinations.Leave) {
                RequestLeaveScreen(onNavBack = { navController.popBackStack() }) { date, reason, appliedDate, status ->
                    leaveViewModel.applyLeaveForUser(
                        Leave(userId = user?.uid, date = date, reason = reason, appliedDate = appliedDate, managerId = user?.managerId, status = status)
                    )
                    navController.popBackStack()
                }
            }
            composable(route = HomeDestinations.Issue) {
                IssueScreen(onNavBack = { navController.popBackStack() }) { title, desc, date ->
                    issueViewModel.applyIssue(Issue(
                        userId = user?.uid,
                        title = title,
                        description = desc,
                        date = date,
                        managerId = user?.managerId,
                        status = "Raised"
                    ))
                    navController.popBackStack()
                }
            }
            composable(route = HomeDestinations.Health) {
                HealthPolicyScreen {
                    navController.popBackStack()
                }
            }
            composable(route = HomeDestinations.Overtime) {
                OvertimePolicyScreen {
                    navController.popBackStack()
                }
            }
        }
    }
}
