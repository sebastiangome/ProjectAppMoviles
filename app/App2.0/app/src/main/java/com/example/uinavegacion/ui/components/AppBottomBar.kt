package com.example.uinavegacion.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onHome: () -> Unit,
    onGames: () -> Unit,
    onCart: () -> Unit,
    cartCount: Int = 0
) {
    NavigationBar(modifier = Modifier) {
        NavigationBarItem(
            selected = currentRoute?.contains("home") == true,
            onClick = onHome,
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute?.contains("games") == true,
            onClick = onGames,
            icon = { Icon(Icons.Filled.SportsEsports, contentDescription = "Juegos") },
            label = { Text("Juegos") }
        )
        NavigationBarItem(
            selected = currentRoute?.contains("cart") == true,
            onClick = onCart,
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
            label = { Text(if (cartCount > 0) "Carrito (${cartCount})" else "Carrito") }
        )
    }
}
