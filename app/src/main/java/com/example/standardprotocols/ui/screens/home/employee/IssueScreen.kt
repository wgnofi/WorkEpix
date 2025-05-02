package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme
import java.time.Instant


@Composable
fun IssueScreen(
    onNavBack: () -> Unit,
    onRaiseIssue: (title: String, desc: String, date: String) -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(onNavBack = onNavBack, "Raise An Issue")
            Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Title of the issue")
                    }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    label = {
                        Text("Describe the issue in maximum of 100 words")
                    }
                )
                ElevatedButton(onClick = {
                    onRaiseIssue(title, description, Instant.now().toEpochMilli().millisToDate().toString())
                }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Raise the issue")
                }
            }
        }
}


@Preview
@Composable
private fun IssueScreenPreview() {
    StandardProtocolsTheme {
        Surface {
//            IssueScreen({}, {})
        }
    }
}