package com.example.uinavegacion.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(nav: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Ajustes") }) }) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Button(onClick = { nav.navigate(Route.SettingsLanguage.path) }) { Text("Idioma") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.SettingsTheme.path) }) { Text("Tema") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.SettingsPrivacy.path) }) { Text("Privacidad") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.About.path) }) { Text("Acerca de") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.Terms.path) }) { Text("Términos") }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { nav.navigate(Route.Privacy.path) }) { Text("Política de Privacidad") }
        }
    }
}
