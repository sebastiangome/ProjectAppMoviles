package com.example.uinavegacion.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(nav: NavHostController) {
    var text by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBar(title = { Text("Enviar feedback") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Comentario") })
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.popBackStack() }) { Text("Enviar") }
        }
    }
}
