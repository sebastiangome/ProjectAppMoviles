package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Pantalla Home")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.navigate(Route.Login.path) }) { Text("Ir a Login") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.Register.path) }) { Text("Ir a Registro") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.Games.path) }) { Text("Ir a Juegos") }
        }
    }
}
