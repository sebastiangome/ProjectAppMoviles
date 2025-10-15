package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uinavegacion.viewmodel.CartViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable  
fun GameDetailScreen(nav: NavHostController, gameId: String, cartViewModel: CartViewModel = viewModel()) {
    
    // Modelo simple del juego
    data class Game(val id: String, val name: String, val price: Double, val category: String, val stock: Int)

    // Lista simple de juegos (misma que en GamesScreen)
    val games = listOf(
        Game("1", "Super Mario Bros", 29.99, "Plataformas", 15),
        Game("2", "The Legend of Zelda", 39.99, "Aventura", 8),
        Game("3", "Pokémon Red", 24.99, "RPG", 20),
        Game("4", "Sonic the Hedgehog", 19.99, "Plataformas", 12),
        Game("5", "Final Fantasy VII", 49.99, "RPG", 5),
        Game("6", "Street Fighter II", 14.99, "Arcade", 10),
        Game("7", "Minecraft", 26.99, "Aventura", 25),
        Game("8", "Call of Duty", 59.99, "Acción", 7),
        Game("9", "FIFA 24", 69.99, "Deportes", 18),
        Game("10", "The Witcher 3", 39.99, "RPG", 6)
    )

    val game = games.find { it.id == gameId } ?: games[0]
    val isInCart by remember { derivedStateOf { cartViewModel.isInCart(gameId) } }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text(game.name, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) 
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen del juego
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("Juego", style = MaterialTheme.typography.displayLarge)
            }

            // Información principal
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "$${game.price}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Stock disponible: ${game.stock}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (game.stock > 5) MaterialTheme.colorScheme.onSurfaceVariant 
                               else MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Descripción del juego ${game.name} en la categoría ${game.category}.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Detalles del juego
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Detalles del Juego",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Categoría:", fontWeight = FontWeight.Medium)
                        Text(game.category)
                    }
                    Spacer(Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Precio:", fontWeight = FontWeight.Medium)
                        Text("$${game.price}")
                    }
                    Spacer(Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Stock:", fontWeight = FontWeight.Medium)
                        Text("${game.stock} unidades")
                    }
                }
            }

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (game.stock > 0) {
                            cartViewModel.addGame(game.id, game.name, game.price)
                        }
                    },
                    enabled = game.stock > 0 && !isInCart,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        if (isInCart) "En Carrito" 
                        else if (game.stock > 0) "Agregar al Carrito"
                        else "Sin Stock"
                    )
                }
                
                Button(
                    onClick = { nav.navigate(Route.Cart.path) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Carrito")
                }
            }
        }
    }
}