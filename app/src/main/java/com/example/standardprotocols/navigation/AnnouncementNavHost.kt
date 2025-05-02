package com.example.standardprotocols.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardprotocols.data.Announcement
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.screens.announcement.AnnouncementScreen
import com.example.standardprotocols.ui.screens.announcement.AnnouncementViewModel
import com.example.standardprotocols.ui.screens.announcement.PostScreen

object ADestinations {
    const val Announcement = "APage"
    const val Post = "PostPage"
}
@Composable
fun AnnouncementNavHost(
    user: User?,
    announcementViewModel: AnnouncementViewModel = hiltViewModel()
) {
    val aNavController = rememberNavController()
    Scaffold { padding ->
        NavHost(navController = aNavController, startDestination = ADestinations.Announcement, modifier = Modifier.padding(padding)) {
            composable(route = ADestinations.Announcement) {
                AnnouncementScreen(user?.role ?: "-1",
                    onPostClick = {
                        aNavController.navigate(ADestinations.Post)
                    },
                    announcementViewModel = announcementViewModel)
            }
            composable(route = ADestinations.Post) {
                PostScreen ({
                    aNavController.popBackStack()
                },
                    onSubmit = { a: Announcement ->
                        announcementViewModel.addAnnouncement(a)
                        aNavController.popBackStack()
                    }, user)
            }
        }
    }
}