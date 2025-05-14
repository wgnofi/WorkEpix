package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.standardprotocols.R
import com.example.standardprotocols.common.TopBar

const val OVERTIME = "Wider Recognition for Achievers\n" +
        "The Employee Recognition Initiative singles out exceptional employees who deserve kudos for their achievements from colleagues and peer organizations outside the company. Cisco managers can submit nominations for awards given by groups outside the company, such as Black Engineer of the Year, Asian Engineer of the Year, Women of Color Research Sciences and Technology Awards, Chinese Institute of Engineers/USA, and the YWCA Tribute to Women and Industry Program.\n" +
        "Direct Access to Cisco Executives\n" +
        "Birthday Chats, a long-standing Cisco tradition, offers Cisco Chairman and CEO John Chambers an occasion for celebrating a personal day with employees while giving them a venue for asking questions and sharing information. The Birthday Chats, held every other month, provide an open forum where employees can voice concerns and get straight answers about a variety of topics. Employees in Raleigh, North Carolina, and Richardson, Texas, can participate remotely by streaming video, and other remotely located employees can attend using Cisco TV. A video of the session, organized by the questions asked, is made available to all employees following the event.\n" +
        "Employee Issue Resolution\n" +
        "From time to time, issues with employees or former employees arise that cannot be resolved through open communication. Cisco has an issue resolution process for handling these problems promptly and fairly.\n" +
        " \n" +
        "Managers are responsible for considering the employee’s issue, attempting to reach resolution quickly, and communicating the reason and rationale for the decision to the employee. If the manager does not resolve the issue to the employee’s satisfaction, or if the employee does not feel comfortable discussing the issue with his or her immediate manager, the employee may go to a manager at the next level. Should the problem remain unresolved, the employee may present the issue to the local Human Resources (HR) group. An issue is a good candidate for consideration if it has impacted a term or condition of the employee’s employment. Examples include harassment in the workplace, termination, demotion, or denial of a promotion.\n"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OvertimePolicyScreen(
    onBack :() -> Unit,
    genViewModel: GenViewModel = hiltViewModel()
) {
    var c by remember {
        mutableStateOf(false)
    }
    val gen by genViewModel.response.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onBack, name = "Overtime Policy")
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        OVERTIME
                    )
                }
            }
            FloatingActionButton(onClick = {c = true}, modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)) {
                Icon(painter = painterResource(R.drawable.ai), contentDescription = null)
            }
        }
        if (c) {
            LaunchedEffect(Unit) {
                genViewModel.getResponseFor(OVERTIME)
            }
            ModalBottomSheet(onDismissRequest = {c = false}) {
                Column(modifier = Modifier.fillMaxWidth().height(700.dp).padding(16.dp)) {
                    when (gen) {
                        is ApiResponse.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text("Loading...")
                            }
                        }

                        is ApiResponse.Error -> {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text("Loading...")
                            }
                        }
                        is ApiResponse.Success -> {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text((gen as ApiResponse.Success).data)
                            }
                        }
                    }
                }
            }
        }
    }
}