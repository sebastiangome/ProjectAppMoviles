package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(nav: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Mi Perfil") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Nombre: Demo")
            Text("Email: demo@demo.com")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.navigate(Route.ProfileEdit.path) }) { Text("Editar Perfil") }
            Button(onClick = { nav.navigate(Route.ChangePassword.path) }) { Text("Cambiar contraseña") }
        }
    }
}
