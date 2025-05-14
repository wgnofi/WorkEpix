package com.example.standardprotocols.ui.screens.home.manager

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.R
import com.example.standardprotocols.data.IssueListResult
import com.example.standardprotocols.ui.screens.home.employee.IssueViewModel
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme

@Composable
fun ManagerHomeScreen(
    issueViewModel: IssueViewModel,
    onPendingLeaveReqClick: () -> Unit,
    onApprovedLeave: () -> Unit,
    onRejectedLeave: () -> Unit,
    onIssueClick: () -> Unit,
    onFixedIssues: () -> Unit,
    onProfileClick: () -> Unit
) {
    val count by issueViewModel.issueCountForUser.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Hello Karthick", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(
                    onClick = onProfileClick, modifier = Modifier.border(
                        width = 2.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Gray, Color.LightGray
                            )
                        ), shape = RoundedCornerShape(10.dp)
                    )
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }

            }
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                ElevatedCard(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Managing")
                        Text("1", fontSize = 75.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                ElevatedCard(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Active Issues")
                        Text(count.toString(), fontSize = 75.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("People")
                    Icon(
                        painter = painterResource(
                            R.drawable.people
                        ), contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Raghul A")
                    Text("sraraghul@gmail.com")
                }
            }
            Text("Leave Requests", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            ElevatedCard(
                onClick = onPendingLeaveReqClick,modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onPendingLeaveReqClick
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pending Leave Requests")
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onApprovedLeave
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Approved Leave Requests")
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onRejectedLeave
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Rejected Leave Requests")
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
            Text("Issue Status", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
            ElevatedCard(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = onIssueClick
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Current Issues")
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

