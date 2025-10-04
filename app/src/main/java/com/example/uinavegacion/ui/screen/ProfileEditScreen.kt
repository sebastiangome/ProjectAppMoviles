package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(nav: NavHostController) {
    var name by remember { mutableStateOf("Demo") }
    var phone by remember { mutableStateOf("999999999") }
    Scaffold(topBar = { TopAppBar(title = { Text("Editar Perfil") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { nav.popBackStack() }) { Text("Guardar") }
        }
    }
}
