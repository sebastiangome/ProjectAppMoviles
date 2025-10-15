package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGamesScreen(navController: NavHostController) {
    // Datos de ejemplo
    val games = remember {
        listOf(
            "JUEGO001" to "Super Mario Bros",
            "JUEGO002" to "The Legend of Zelda",
            "JUEGO003" to "Pokémon Red",
            "JUEGO004" to "Sonic the Hedgehog",
            "JUEGO005" to "Final Fantasy VII"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Juegos") },
                navigationIcon = {
                    TextButton(onClick = { navController.navigate(Route.AdminDashboard.path) }) {
                        Text("← Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Route.AdminAddGame.path) }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lista de Juegos (${games.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { navController.navigate(Route.AdminAddGame.path) }) {
                        Text("Agregar Nuevo")
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            items(games) { (id, name) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ID: $id",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Precio: $29.99",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { navController.navigate(Route.AdminEditGame.build(id)) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text("Editar")
                            }
                            OutlinedButton(
                                onClick = { /* Acción de eliminar */ }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(80.dp)) // Espacio para el FAB
            }
        }
    }
}
