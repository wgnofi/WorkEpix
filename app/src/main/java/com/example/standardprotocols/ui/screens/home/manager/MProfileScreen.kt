package com.example.standardprotocols.ui.screens.home.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.data.User

@Composable
fun MProfileScreen(
    onBack: () -> Unit,
    user: User?,
    onSignOut: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onNavBack = onBack, name = "Profile")
        ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Manager", fontWeight = FontWeight.Bold)
                Text(user?.displayName ?: "No Name", fontWeight = FontWeight.SemiBold)
            }
        }
        ElevatedButton(onSignOut, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Log out")
            Spacer(modifier = Modifier.padding(8.dp))
            Icon(imageVector = Icons.AutoMirrored.Default.ExitToApp, contentDescription = null)
        }
    }
}