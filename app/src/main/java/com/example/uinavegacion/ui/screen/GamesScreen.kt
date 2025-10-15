package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uinavegacion.viewmodel.SearchViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(nav: NavHostController, searchViewModel: SearchViewModel = viewModel(), cartViewModel: com.example.uinavegacion.viewmodel.CartViewModel = viewModel()) {
    // Modelo simple de juego con stock
    data class Game(
        val id: String, 
        val name: String, 
        val price: Double, 
        val category: String, 
        val stock: Int
    )

    // Lista simple de juegos con stock
    val allGames = listOf(
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
    val query by searchViewModel.query.collectAsState()
    // categorías disponibles
    val categories = listOf("Todos", "Plataformas", "Aventura", "RPG", "Arcade", "Acción", "Deportes")
    var selectedCategory by remember { mutableStateOf("Todos") }

    val games = allGames.filter { game ->
        val matchesCategory = selectedCategory == "Todos" || game.category == selectedCategory
        val matchesQuery = query.isBlank() || game.name.contains(query, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Juegos", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Descubre nuestra colección de videojuegos",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))

                    // Fila de chips de categorías
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(start = 4.dp, end = 4.dp)) {
                        categories.forEach { cat ->
                            val selected = cat == selectedCategory
                            FilterChip(
                                selected = selected,
                                onClick = { selectedCategory = cat },
                                label = { Text(cat) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }

            items(games) { game ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    onClick = { nav.navigate(Route.GameDetail.build(game.id)) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Placeholder para imagen del juego
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🎮",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = game.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "ID: ${game.id}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "$${game.price}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Stock: ${game.stock}",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (game.stock > 5) MaterialTheme.colorScheme.onSurfaceVariant 
                                       else MaterialTheme.colorScheme.error
                            )
                        }

                        // Botones de acción
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    if (game.stock > 0 && !cartViewModel.isInCart(game.id)) {
                                        cartViewModel.addGame(game.id, game.name, game.price)
                                    }
                                },
                                enabled = game.stock > 0 && !cartViewModel.isInCart(game.id),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    if (cartViewModel.isInCart(game.id)) "En Carrito"
                                    else if (game.stock > 0) "Agregar"
                                    else "Sin Stock"
                                )
                            }
                            
                            TextButton(
                                onClick = { nav.navigate(Route.GameDetail.build(game.id)) }
                            ) {
                                Text("Ver Más", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Total de juegos: ${games.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
