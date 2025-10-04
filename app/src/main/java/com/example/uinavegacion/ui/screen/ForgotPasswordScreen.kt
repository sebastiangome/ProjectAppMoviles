package com.example.uinavegacion.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBar(title = { Text("Recuperar contraseña") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* enviar correo */ }) { Text("Enviar enlace") }
        }
    }
}
