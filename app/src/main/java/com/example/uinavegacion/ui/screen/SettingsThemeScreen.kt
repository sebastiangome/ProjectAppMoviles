package com.example.uinavegacion.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsThemeScreen(nav: NavHostController) {
    var dark by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("Tema") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = dark, onCheckedChange = { dark = it })
                Spacer(Modifier.width(8.dp))
                Text(if (dark) "Oscuro" else "Claro")
            }
        }
    }
}
