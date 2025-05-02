package com.example.standardprotocols.ui.screens.announcement

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.R
import com.example.standardprotocols.data.AnnouncementListResult
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme

@Composable
fun AnnouncementScreen(
    role: String,
    onPostClick: () -> Unit,
    announcementViewModel: AnnouncementViewModel
) {
    val announcementList by announcementViewModel.activeAnnouncement.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Announcements", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                Surface(
                    border = BorderStroke(
                        2.dp,
                        brush = Brush.linearGradient(listOf(Color.Green, Color.Cyan))
                    ),
                    shadowElevation = 5.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.announce),
                            contentDescription = "announce"
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            when (role) {
                "Manager" -> FloatingActionButton(onClick = onPostClick) {
                    Icon(painter = painterResource(R.drawable.post), contentDescription = "post")
                }
                else -> {}
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                   when(announcementList) {
                       is AnnouncementListResult.Success -> {
                           (announcementList as AnnouncementListResult.Success).list.forEach {
                               Announcement(content = it.postContent ?: "Blank")
                           }
                       } else -> {
                           Log.d("UI", "Announcement UI")
                       }
                   }
                }
            }
        }
    }
}

@Composable
fun Announcement(
    content: String,
) {
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Surface(color = Color.Black, modifier = Modifier.clip(CircleShape)) {
                Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)) {
                    Text("MA", fontSize = 10.sp, color = Color.White)
                }
            }
            Text("Manager", fontWeight = FontWeight.SemiBold,)
        }
        Text(content)
    }
    HorizontalDivider()
}

fun String.firstLetters() = this.split(" ").map { it.first() }.joinToString("")
@Preview
@Composable
private fun APreview() {
    StandardProtocolsTheme(darkTheme = true) {
        Surface {
            Announcement("Hey guys successfully pulled approval for our project")
        }
    }
}
