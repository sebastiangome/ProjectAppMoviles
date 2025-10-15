package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(nav: NavHostController, orderId: String) {
    Scaffold(topBar = { TopAppBar(title = { Text("Orden $orderId") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Detalle de la orden $orderId")
        }
    }
}
