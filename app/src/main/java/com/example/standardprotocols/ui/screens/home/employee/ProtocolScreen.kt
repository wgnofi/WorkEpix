package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.standardprotocols.R
import com.example.standardprotocols.data.User
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme
import com.example.standardprotocols.data.FAQ as f

@Composable
fun ProtocolScreen(
    user: User?,
    healthOnClick: () -> Unit,
    overtimeOnClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLeaveClick:() -> Unit,
    onIssueClick: () -> Unit,
) {
    val listOfFaqs = f.listOfFaqs

    Column(modifier = Modifier.fillMaxSize()) {
        var searchText by remember {
            mutableStateOf("")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Hello ${user?.displayName ?: "Null"}", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(
                    onClick = onProfileClick, modifier = Modifier.border(
                        width = 2.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red, Color.Magenta, Color.Cyan
                            )
                        ), shape = RoundedCornerShape(10.dp)
                    )
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }

            }
        }
        OutlinedTextField(
            value = searchText, onValueChange = {
                searchText = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text("Search any policy")
            }
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Quick View",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    painter = painterResource(R.drawable.policy), contentDescription = null,
                    modifier = Modifier.size(45.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ElevatedCard(onClick = healthOnClick, modifier = Modifier.weight(1f)) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .height(100.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.health),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text("Health Policy")
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))

                ElevatedCard(onClick = overtimeOnClick, modifier = Modifier.weight(1f)) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .height(100.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.overtime),
                            contentDescription = null,
                            modifier = Modifier.size(45.dp)
                        )
                        Text("Overtime Policy")
                    }
                }
            }
            HorizontalDivider()
            Box(modifier = Modifier.padding(16.dp)) {
                ElevatedCard(onClick = onLeaveClick, modifier = Modifier) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Request for absence")
                        Icon(
                            painter = painterResource(R.drawable.leave),
                            contentDescription = null,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }
            }
            Text(
                "Complaint",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )
            Box(modifier = Modifier.padding(16.dp)) {
                ElevatedCard(onClick = onIssueClick, modifier = Modifier) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Raise An Issue")
                        Icon(
                            painter = painterResource(R.drawable.rights),
                            contentDescription = null,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }
            }

            Text(
                "FAQ's",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )
            HorizontalDivider()
            listOfFaqs.forEach { (key, value) ->
                FAQ(key, value)
            }
        }
    }
}



@Composable
fun FAQ(title: String, explanation: String) {
    var visible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.CenterVertically)
        .padding(16.dp)
        .clickable(onClick = { visible = !visible })) {
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(if (visible) title else title.shrinkToFAQ())
                Icon(
                    imageVector = if (!visible) Icons.AutoMirrored.Filled.KeyboardArrowRight else
                        Icons.Default.KeyboardArrowDown, contentDescription = null
                )
        }
        AnimatedVisibility(visible) {
            Text(explanation)
        }
    }

}

fun String.shrinkToFAQ(): String {
    val s = this
    return if (s.length > 35) { s.slice(0..35) + "..."} else s
}

@Preview
@Composable
private fun FAQPreview() {
    StandardProtocolsTheme {
        Surface {
            FAQ(title = "What are the working days", explanation = "9-5 brother!")
        }
    }
}
