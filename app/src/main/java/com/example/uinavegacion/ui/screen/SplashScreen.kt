package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.example.uinavegacion.navigation.*

@Composable
fun SplashScreen(nav: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1200)
        nav.navigate(Route.Home.path) {
            popUpTo(Route.Splash.path) { inclusive = true }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Splash", style = MaterialTheme.typography.headlineMedium)
    }
}
