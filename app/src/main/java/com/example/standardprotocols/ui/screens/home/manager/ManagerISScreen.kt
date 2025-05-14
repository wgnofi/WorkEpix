package com.example.standardprotocols.ui.screens.home.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.data.Issue
import com.example.standardprotocols.data.IssueListResult
import com.example.standardprotocols.ui.screens.home.employee.IssueViewModel
import com.example.standardprotocols.ui.screens.home.employee.getFormattedDate
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme

@Composable
fun ManagerISScreen(
    onNavBack: () -> Unit,
    issueViewModel: IssueViewModel
) {
    val activeIssuesState by issueViewModel.activeIssues.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onNavBack = onNavBack, name = "Raised Issues")
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            when(activeIssuesState) {
                is IssueListResult.Loading -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)) {
                        Text("Loading...")
                    }
                }
                is IssueListResult.Error -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)) {
                        Text("Not Loading Might be an internet Issue!")
                    }
                }
                is IssueListResult.Success -> {
                    val list = (activeIssuesState as IssueListResult.Success).issueList
                    if (list.isNotEmpty()) {
                        list.forEach { issue ->
                            IssueCard(issue, onFix = { id ->
                                issueViewModel.fixIssue(id)
                            })
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)) {
                            Text("No Active Issues, Feel good fam!")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun IssueCard(
    issue: Issue,
    onFix: (id: String) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(issue.title ?: "Not Found", fontSize = 22.sp)
            Text(getFormattedDate(issue.date ?: ""))
        }
        Surface(modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 10.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(issue.description ?: "No description", fontWeight = FontWeight.Medium)
            }
        }
        TextButton(onClick = {
            onFix(issue.id!!)
        },modifier = Modifier.fillMaxWidth().wrapContentWidth(align = Alignment.CenterHorizontally).padding(16.dp)) {
            Text("Mark as Fixed")
        }
    }
}
