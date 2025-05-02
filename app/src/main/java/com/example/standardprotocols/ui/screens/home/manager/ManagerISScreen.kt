package com.example.standardprotocols.ui.screens.home.manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.standardprotocols.common.TopBar

@Composable
fun ManagerISScreen(
    onNavBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onNavBack = onNavBack, name = "Raised Issues")

    }
}


