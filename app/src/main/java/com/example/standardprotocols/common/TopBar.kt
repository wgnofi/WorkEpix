package com.example.standardprotocols.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    onNavBack: () -> Unit,
    name: String
) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        IconButton(onClick = onNavBack) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
        }
        Text(name, fontSize = 18.sp)
    }
}

@Composable
fun TopActionBar(
    onBack: () -> Unit,
    onDone: () -> Unit,
    backText: String,
    doneText: String
) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onBack) {
            Text(backText)
        }
        TextButton(onDone) {
            Text(doneText)
        }
    }
}