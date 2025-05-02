package com.example.standardprotocols.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardprotocols.ui.screens.home.employee.IssueViewModel
import com.example.standardprotocols.ui.screens.home.employee.LeaveViewModel
import com.example.standardprotocols.ui.screens.home.manager.MProfileScreen
import com.example.standardprotocols.ui.screens.home.manager.ManagerALRScreen
import com.example.standardprotocols.ui.screens.home.manager.ManagerHomeScreen
import com.example.standardprotocols.ui.screens.home.manager.ManagerISScreen
import com.example.standardprotocols.ui.screens.home.manager.ManagerLRScreen
import com.example.standardprotocols.ui.screens.home.manager.ManagerRLRScreen
import com.example.standardprotocols.ui.screens.login.LoginViewModel
import com.example.standardprotocols.ui.screens.login.UserViewModel

object MDestinations {
    const val HOME = "MHomePage"
    const val ISSUE = "MIssuePage"
    const val LEAVE_PENDING = "MLeavePage"
    const val LEAVE_APPROVED = "MALeavePage"
    const val LEAVE_REJECTED = "MRLeavePage"
    const val PROFILE = "MProfilePage"
}
@Composable
fun ManagerNavHost(
    onLogOut: () -> Unit,
    loginViewModel: LoginViewModel,
    leaveViewModel: LeaveViewModel,
    issueViewModel: IssueViewModel
) {
    val user by loginViewModel.user.collectAsStateWithLifecycle()
    val mNavController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController = mNavController, startDestination =MDestinations.HOME,
            modifier = Modifier.padding(innerPadding)) {
            composable(route = MDestinations.HOME) {
                ManagerHomeScreen(
                    onPendingLeaveReqClick = {
                    mNavController.navigate(MDestinations.LEAVE_PENDING)
                }, onIssueClick = {
                    mNavController.navigate(MDestinations.ISSUE)
                },
                    onApprovedLeave = {
                        mNavController.navigate(MDestinations.LEAVE_APPROVED)
                    },
                    onRejectedLeave = {
                        mNavController.navigate(MDestinations.LEAVE_REJECTED)
                    },
                    onFixedIssues ={},
                    onProfileClick = {
                        mNavController.navigate(MDestinations.PROFILE)
                    }
                )
            }
            composable(route = MDestinations.LEAVE_PENDING) {
                ManagerLRScreen( {
                    mNavController.popBackStack()
                }, leaveViewModel)
            }
            composable(route = MDestinations.ISSUE) {
                ManagerISScreen {
                    mNavController.popBackStack()
                }
            }
            composable(route = MDestinations.PROFILE) {
                MProfileScreen({
                    mNavController.popBackStack()
                }, user, onLogOut)
            }

            composable(route = MDestinations.LEAVE_APPROVED) {
                ManagerALRScreen({mNavController.popBackStack()},
                    leaveViewModel, user)
            }

            composable(route = MDestinations.LEAVE_REJECTED) {
                ManagerRLRScreen({mNavController.popBackStack()},
                    leaveViewModel, user)
            }
        }
    }
}