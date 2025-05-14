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

const val HEALTH_TEXT = "Benefits for employees:\n" +
        "Inclusion: According to our 2022 Work Trend Index Report, over half (52%) of the people we surveyed feel more valued or included as a remote contributor in meetings. We’ve also watched in-meeting chat become a channel for more people to share their perspectives.\n" +
        "Empathy: Since the beginning of the pandemic, 62% of people we surveyed said they feel more empathetic toward their colleagues now that they have a better view of their life at home.\n" +
        "Work-life integration: When working from home, people are better able to meet obligations outside of work—such as pursuing job training and education, giving care to family members, picking children up from school, and well as exercising and eating well.\n" +
        "Lower stress: Numerous studies show that job-related stress is the main source of stress for adults and that it has escalated dramatically in recent years. According to the CDC, establishing work schedules that are compatible with demands outside one’s job is one of the top recommendations to reduce job stress.\n" +
        "Reduced need for paid time off: A flexible schedule allows people to reserve sick days to handle personal tasks like banking, errands, and medical appointments. With more flexibility, people can reserve sick days for when they are truly needed and preserve vacation days for more extended down time.\n" +
        "Overall increased wellbeing. Find ways to be creative and open up some personal time throughout the day, no matter where you do your work. Instead of another video call, propose an audio-only call so you can walk and talk or find a change in scenery. (A 2020 study of information workers found that moderate physical activity increases productivity and boosts moods.) To relax your mind between projects or decrease stress before a big presentation try our mindfulness content from providers like Headspace, which offers guided meditations right within Microsoft Teams."

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthPolicyScreen(
    onBack: () -> Unit,
    genViewModel: GenViewModel = hiltViewModel()
) {
    var c by remember {
        mutableStateOf(false)
    }
    val gen by genViewModel.response.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onBack, name = "Health Policy")
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        HEALTH_TEXT
                    )
                }
            }
            FloatingActionButton(onClick = {c = true}, modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)) {
                Icon(painter = painterResource(R.drawable.ai), contentDescription = null)
            }
        }
        if (c) {
            LaunchedEffect(Unit) {
                genViewModel.getResponseFor(HEALTH_TEXT)
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