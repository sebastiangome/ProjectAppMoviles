package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddGameScreen(
    navController: NavHostController,
    gameId: String? = null // <-- CORRECCIÓN 1: gameId ahora es opcional
) {
    // --- Lógica para diferenciar entre crear y editar ---
    val isEditing = gameId != null
    val screenTitle = if (isEditing) "Editar Juego" else "Agregar Nuevo Juego"
    val buttonText = if (isEditing) "Actualizar Juego" else "Guardar Juego"

    var gameName by remember { mutableStateOf("") }
    var gameDescription by remember { mutableStateOf("") }
    var gamePrice by remember { mutableStateOf("") }
    var gameCategory by remember { mutableStateOf("") }
    var gameDeveloper by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    val successMessage = if (isEditing) "El juego ha sido actualizado." else "El juego ha sido agregado."

    // --- Efecto para cargar datos si estamos editando ---
    LaunchedEffect(gameId) {
        if (isEditing) {
            // Aquí iría la lógica para cargar los datos del juego desde la base de datos o ViewModel
            // usando el 'gameId'. Por ahora, simulamos la carga de datos.
            gameName = "Juego Cargado para Editar"
            gameDescription = "Descripción del juego existente."
            gamePrice = "49.99"
            gameCategory = "RPG"
            gameDeveloper = "Developer Famoso"
        }
    }

    if (showSuccess) {
        AlertDialog(
            onDismissRequest = { showSuccess = false },
            title = { Text("¡Éxito!") },
            text = { Text(successMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccess = false
                        // Navega de vuelta a la lista de juegos y limpia el historial para no volver a la pantalla de edición/creación.
                        navController.navigate(Route.AdminGames.path) {
                            popUpTo(Route.AdminGames.path) { inclusive = true }
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) }, // <-- Título dinámico
                navigationIcon = {
                    // Botón para volver atrás o cancelar
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("← Cancelar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Información del Juego",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = gameName,
                onValueChange = { gameName = it },
                label = { Text("Nombre del Juego") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = gameDescription,
                onValueChange = { gameDescription = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = gamePrice,
                    onValueChange = { gamePrice = it },
                    label = { Text("Precio") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = gameCategory,
                    onValueChange = { gameCategory = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = gameDeveloper,
                onValueChange = { gameDeveloper = it },
                label = { Text("Desarrollador") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (gameName.isNotEmpty() && gamePrice.isNotEmpty()) {
                        isSaving = true
                        // Simular guardado/actualización en el servidor o DB
                        showSuccess = true
                        isSaving = false
                    }
                },
                enabled = !isSaving && gameName.isNotEmpty() && gamePrice.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Guardando...")
                } else {
                    Text(buttonText) // <-- Texto del botón dinámico
                }
            }

            OutlinedButton(
                onClick = { navController.popBackStack() }, // Usamos popBackStack para volver a la pantalla anterior
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
