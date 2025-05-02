package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.standardprotocols.common.TopBar

@Composable
fun OvertimePolicyScreen(
    onBack :() -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onBack, name = "Health Policy")
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text("Wider Recognition for Achievers\n" +
                    "The Employee Recognition Initiative singles out exceptional employees who deserve kudos for their achievements from colleagues and peer organizations outside the company. Cisco managers can submit nominations for awards given by groups outside the company, such as Black Engineer of the Year, Asian Engineer of the Year, Women of Color Research Sciences and Technology Awards, Chinese Institute of Engineers/USA, and the YWCA Tribute to Women and Industry Program.\n" +
                    "Direct Access to Cisco Executives\n" +
                    "Birthday Chats, a long-standing Cisco tradition, offers Cisco Chairman and CEO John Chambers an occasion for celebrating a personal day with employees while giving them a venue for asking questions and sharing information. The Birthday Chats, held every other month, provide an open forum where employees can voice concerns and get straight answers about a variety of topics. Employees in Raleigh, North Carolina, and Richardson, Texas, can participate remotely by streaming video, and other remotely located employees can attend using Cisco TV. A video of the session, organized by the questions asked, is made available to all employees following the event.\n" +
                    "Employee Issue Resolution\n" +
                    "From time to time, issues with employees or former employees arise that cannot be resolved through open communication. Cisco has an issue resolution process for handling these problems promptly and fairly.\n" +
                    " \n" +
                    "Managers are responsible for considering the employee’s issue, attempting to reach resolution quickly, and communicating the reason and rationale for the decision to the employee. If the manager does not resolve the issue to the employee’s satisfaction, or if the employee does not feel comfortable discussing the issue with his or her immediate manager, the employee may go to a manager at the next level. Should the problem remain unresolved, the employee may present the issue to the local Human Resources (HR) group. An issue is a good candidate for consideration if it has impacted a term or condition of the employee’s employment. Examples include harassment in the workplace, termination, demotion, or denial of a promotion.\n")
        }
    }
}