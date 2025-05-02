package com.example.standardprotocols.ui.screens.announcement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardprotocols.common.TopActionBar
import com.example.standardprotocols.data.Announcement
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme


@Composable
fun PostScreen(
    onCancel: () -> Unit,
    onSubmit: (a: Announcement) -> Unit,
    user: User?
) {
    var postContent by rememberSaveable {
        mutableStateOf("")
    }

        Column(modifier = Modifier.fillMaxSize()) {
            TopActionBar(onCancel, {
                onSubmit(Announcement(uid = user?.uid, postContent = postContent))
            }, "cancel", "post")
            Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = postContent,
                onValueChange = {
                    postContent = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Write anything!")
                },
                minLines = 7
            )
            }
        }
}

