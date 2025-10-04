package com.example.uinavegacion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.uinavegacion.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(nav: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Bienvenido") }) },
        bottomBar = {
            BottomAppBar {
                Spacer(Modifier.weight(1f))
                Button(onClick = {
                    nav.navigate(Route.Login.path) {
                        popUpTo(Route.Onboarding.path) { inclusive = true }
                    }
                }) { Text("Comenzar") }
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner).fillMaxSize()) {
            Text("Onboarding: explica tu app en 2-3 pasos.")
        }
    }
}
