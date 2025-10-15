package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(nav: NavHostController) {
    var p1 by remember { mutableStateOf("") }
    var p2 by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBar(title = { Text("Cambiar contraseña") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = p1, onValueChange = { p1 = it }, label = { Text("Nueva contraseña") })
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = p2, onValueChange = { p2 = it }, label = { Text("Repetir contraseña") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { nav.popBackStack() }) { Text("Actualizar") }
        }
    }
}
