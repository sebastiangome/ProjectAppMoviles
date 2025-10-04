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
fun CartScreen(nav: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Carrito") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Productos en el carrito…")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.navigate(Route.Checkout.path) }) { Text("Ir a pagar") }
        }
    }
}
