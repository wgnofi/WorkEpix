package com.example.standardprotocols.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.standardprotocols.MainNav
import com.example.standardprotocols.R
import com.example.standardprotocols.navigation.LoginNav
import com.example.standardprotocols.ui.theme.StandardProtocolsTheme

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
    onLogIn: (String, String) -> Unit,
) {
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = loginState) {
        when(loginState) {
            is LoginState.LoggedIn, is LoginState.AlreadyLoggedIn, is LoginState.Success-> {
                navController.navigate(LoginNav.LoggedIn.name) {
                    popUpTo(LoginNav.Login.name) { inclusive = false }
                }
            }
            else -> {
                println("Unhandled LoginState: $loginState")
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Standard Protocols", fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Surface(modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp),
                shadowElevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(width = 2.dp, color = Color.DarkGray)
            ) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Column {
                        OutlinedTextField(
                            value = email, onValueChange = { email = it }, modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            label = {
                                Text("Enter your username/email")
                            })
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                        OutlinedTextField(
                            value = password, onValueChange = { password = it }, modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                            label = {
                                Text("Enter your password")
                            },
                            visualTransformation = PasswordVisualTransformation())
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                        ElevatedButton(onClick = {onLogIn(email, password)}, modifier = Modifier.weight(1f)) {
                            Text("Log in")
                        }
                    }
                    Text("Or")
                    ElevatedButton(onClick = {}, colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text("Sign in with Google")
                            Image(painter = painterResource(R.drawable.g_logo), contentDescription = null)
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    StandardProtocolsTheme {
        Surface {
//            LoginScreen({})
        }
    }
}