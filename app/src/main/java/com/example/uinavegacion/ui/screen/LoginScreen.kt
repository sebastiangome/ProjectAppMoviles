package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Iniciar sesión") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, singleLine = true)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Password") }, singleLine = true)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { nav.navigate(Route.Home.path) }) { Text("Entrar") }
            TextButton(onClick = { nav.navigate(Route.Register.path) }) { Text("Crear cuenta") }
        }
    }
}
