package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                actions = {
                    TextButton(onClick = { navController.navigate(Route.Home.path) }) {
                        Text("Salir")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Dashboard Administrativo",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Gestiona tu plataforma desde aquí",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                // Estadísticas rápidas
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Estadísticas Rápidas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("1,234", style = MaterialTheme.typography.headlineSmall)
                                Text("Usuarios", style = MaterialTheme.typography.bodyMedium)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("89", style = MaterialTheme.typography.headlineSmall)
                                Text("Juegos", style = MaterialTheme.typography.bodyMedium)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("567", style = MaterialTheme.typography.headlineSmall)
                                Text("Órdenes", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Gestión de Contenido",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(Route.AdminGames.path) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Gestionar Juegos",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Agregar, editar y eliminar juegos del catálogo",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text("→", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(Route.AdminUsers.path) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Gestionar Usuarios",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Ver y administrar usuarios registrados",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text("→", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }

            item {
                Text(
                    text = "Acciones Rápidas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(Route.AdminAddGame.path) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Agregar Juego")
                    }
                    Button(
                        onClick = { navController.navigate(Route.AdminUsers.path) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ver Usuarios")
                    }
                }
            }
        }
    }
}
