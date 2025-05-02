package com.example.standardprotocols.ui.screens.home.manager

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.data.Leave
import com.example.standardprotocols.data.LeaveListResult
import com.example.standardprotocols.ui.screens.home.employee.LeaveViewModel
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme

@Composable
fun ManagerLRScreen(
    onBack: () -> Unit,
    leaveViewModel: LeaveViewModel
) {
    val leaves by leaveViewModel.activePendingLeaves.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onNavBack = onBack, name = "Leave Requests")
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            when (leaves) {
                is LeaveListResult.Success -> {
                    (leaves as LeaveListResult.Success).leaveList.forEach {
                        MLRCard(it, {
                            leaveViewModel.approveLeave(it.id.toString())
                        }, {
                            leaveViewModel.rejectLeave(it.id.toString())
                        })
                    }
                }

                is LeaveListResult.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    ) {
                        Text("Loading...")
                    }
                }

                is LeaveListResult.Error -> {
                    Log.d("LEAVE", "viewModel error in fetching pending leaves")
                }
            }
        }
    }

}

@Composable
fun MLRCard(
    leave: Leave,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(leave.date.toString())
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text("From:", fontWeight = FontWeight.SemiBold)
            Text("Raghul.A")
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text("Reason:", fontWeight = FontWeight.SemiBold)
            Text(leave.reason.toString())
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ElevatedButton(onClick = onReject, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
                ElevatedButton(onClick = onApprove, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            }
        }
    }
}


