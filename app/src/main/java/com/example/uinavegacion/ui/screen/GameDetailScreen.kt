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
fun GameDetailScreen(nav: NavHostController, gameId: String) {
    Scaffold(topBar = { TopAppBar(title = { Text("Juego $gameId") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Detalle del juego $gameId")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { nav.navigate(Route.GameReviews.build(gameId)) }) { Text("Ver reseñas") }
            Button(onClick = { nav.navigate(Route.NewReview.build(gameId)) }) { Text("Escribir reseña") }
            Button(onClick = { nav.navigate(Route.Cart.path) }) { Text("Agregar al carrito") }
        }
    }
}
