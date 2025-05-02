package com.example.standardprotocols.ui.screens.home.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.R
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.data.Leave
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.screens.home.employee.LeaveViewModel

@Composable
fun ManagerALRScreen(
    onBack: () -> Unit,
    leaveViewModel: LeaveViewModel,
    user: User?
) {
    LaunchedEffect(Unit) {
        leaveViewModel.getApprovedLeaves(user?.uid!!)
    }
    val leaves by leaveViewModel.approvedLeaveList.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onNavBack = onBack, name = "Approved Leaves")
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            leaves.forEach {
                MALRCard(it)
            }
        }
    }

}

@Composable
fun MALRCard(
    leave: Leave,
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(leave.date.toString())
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text("From:", fontWeight = FontWeight.SemiBold)
                Icon(painter = painterResource(R.drawable.approved), contentDescription = null)
            }
            Text("Raghul.A")
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text("Reason:", fontWeight = FontWeight.SemiBold)
            Text(leave.reason.toString())
        }
    }
}

