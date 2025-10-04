package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewReviewScreen(nav: NavHostController, gameId: String) {
    var rating by remember { mutableStateOf("5") }
    var comment by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBar(title = { Text("Nueva reseña — $gameId") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("Calificación 1..5") })
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = comment, onValueChange = { comment = it }, label = { Text("Comentario") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = { nav.popBackStack() }) { Text("Publicar") }
        }
    }
}
