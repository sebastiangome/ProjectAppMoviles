package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(nav: NavHostController) {
    val fake = listOf("JUEGO001", "JUEGO002", "JUEGO003")
    Scaffold(topBar = { TopAppBar(title = { Text("Catálogo de Juegos") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            fake.forEach { id ->
                Button(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    onClick = { nav.navigate(Route.GameDetail.build(id)) }
                ) { Text("Ver detalle $id") }
            }
        }
    }
}
