package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.R
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.data.Leave
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.screens.login.UserViewModel
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavBack: () -> Unit,
    onLogOut: () -> Unit,
    user: User?,
    userViewModel: UserViewModel,
    leaveViewModel: LeaveViewModel,
    issueViewModel: IssueViewModel
) {
    val managerName by userViewModel.managerName.collectAsStateWithLifecycle()
    val leaveList by leaveViewModel.leaveList.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    var bottomSheetState by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        count = issueViewModel.getRaisedIssuesCount(user?.uid!!)
        leaveViewModel.getLeaveListForUser(user.uid)
    }

    Column(modifier = Modifier.fillMaxSize()) {
           TopBar(onNavBack, "Profile")
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation(3.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(user?.displayName ?: "Not Found", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                            Surface(
                                shape = CircleShape, color = Color.Black,
                                shadowElevation = 10.dp
                            ) {
                                Box(modifier = Modifier.padding(16.dp)) {
                                    Text(user?.displayName?.firstTwo() ?: "Not Found", fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation(3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            RowFill("Designation:", "${user?.role}")
                            Spacer(modifier = Modifier.padding(8.dp))
                            RowFill("Reporting to:", "$managerName")
                        }
                    }
                    RaisedIssues(count)
                    LeavesTaken { bottomSheetState = true }
                    LogOut(onLogOut)
                    Text(
                        "Standard Protocols App v1.0.0",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    )
                }
            }
        if (bottomSheetState) {
            Column(modifier = Modifier.fillMaxSize()) {
                ModalBottomSheet(onDismissRequest = {bottomSheetState = false}) {
                    leaveList.forEach {
                        LeaveCard(it)
                    }
                }
            }
        }
        }
}

@Composable
fun LeaveCard(l: Leave) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(l.date.toString(), fontSize = 18.sp)
            Icon(painter = painterResource(getDrawable(l.status.toString())), contentDescription = null)
        }
    }
}

fun getDrawable(status: String):Int {
    return when(status) {
        "Pending" -> R.drawable.pending
        "Approved" -> R.drawable.approved
        "Rejected" -> R.drawable.rejected
        else -> R.drawable.pending
    }
}

@Preview
@Composable
private fun LeaveCardPreview() {
    StandardProtocolsTheme {
        Surface {
            LeaveCard(
                Leave(

                )
            )
        }
    }
}

@Composable
fun LeavesTaken(
    onClick: () -> Unit
) {
    ElevatedCard(onClick,modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Leaves Taken", fontWeight = FontWeight.SemiBold)
                Text("1/50")
            }
            Text("You have 49 days more", fontSize = 12.sp)
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("View leave status")
                Icon(imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}
@Composable
fun LogOut(
    onLogOut: () -> Unit
) {
    ElevatedCard(onClick = onLogOut,modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp)) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Log out", fontWeight = FontWeight.SemiBold, color = Color.Red)
            Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
        }
    }
}
@Composable
fun RaisedIssues(
    count: Int
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text("Raised Issues", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("You've raised $count Issues")
            }
        }
    }
}

fun String.firstTwo(): String {
    return this.map { it.uppercase() }.subList(0,2).joinToString("")
}

@Composable
private fun RowFill(
    first: String,
    second: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(first, fontWeight = FontWeight.SemiBold)
        Text(second)
    }
}
