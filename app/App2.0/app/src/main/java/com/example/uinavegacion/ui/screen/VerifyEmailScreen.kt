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
fun VerifyEmailScreen(nav: NavHostController, email: String) {
    Scaffold(topBar = { TopAppBar(title = { Text("Verificar email") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Te enviamos un código a: $email")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.navigate(Route.Home.path) }) { Text("He verificado") }
        }
    }
}
