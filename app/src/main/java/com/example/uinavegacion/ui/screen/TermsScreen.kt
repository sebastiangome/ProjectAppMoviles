package com.example.uinavegacion.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen(nav: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Términos y condiciones") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Texto legal de términos…")
        }
    }
}
