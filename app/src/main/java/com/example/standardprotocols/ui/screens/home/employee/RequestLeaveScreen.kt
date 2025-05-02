package com.example.standardprotocols.ui.screens.home.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardprotocols.common.TopBar
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme
import java.time.Instant
import java.util.Date


@Composable
fun RequestLeaveScreen(
    onNavBack: () -> Unit,
    onSubmit: (date: String, reason: String, appliedDate: String, status: String) -> Unit
) {
    var dateChosen by rememberSaveable {
        mutableLongStateOf(0)
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    var fieldEnabledAndDateSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var dialogState by rememberSaveable {
        mutableStateOf(false)
    }
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(onNavBack, "Request Leave")
            Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ElevatedCard(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(if (!fieldEnabledAndDateSelected) "Choose a date" else "${dateChosen.millisToDate()}", fontWeight = FontWeight.SemiBold)
                        IconButton(onClick = {
                            dialogState = true
                        }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Color.DarkGray)
                        }
                    }
                }
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    label = {
                        Text("Brief about in maximum of 100 words")
                    },
                    enabled = fieldEnabledAndDateSelected
                )
                ElevatedButton(onClick = {
                    onSubmit(
                        dateChosen.millisToDate().toString(),
                        description,
                        Instant.now().toEpochMilli().millisToDate().toString(),
                        "Pending"
                    )
                }, enabled = fieldEnabledAndDateSelected, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Request for absence")
                }
            }
        }
    if (dialogState) {
        DatePickerModal(
            onDateSelected = {
                dateChosen = it!!
                fieldEnabledAndDateSelected = true
            },
            onDismiss = {
                dialogState = false
            }
        )
    }
}

fun Long.millisToDate(): Date {
    return Date(this)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit,
                    onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview
@Composable
private fun RequestLeaveScreenPreview() {
    StandardProtocolsTheme {
        Surface {
//            RequestLeaveScreen({}, )
        }
    }
}