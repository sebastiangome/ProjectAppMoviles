package com.example.uinavegacion.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.uinavegacion.ui.components.AppBottomBar
import com.example.uinavegacion.ui.components.AppDrawer
import com.example.uinavegacion.ui.components.AppTopBar
import com.example.uinavegacion.ui.screen.*
import com.example.uinavegacion.viewmodel.CartViewModel
import com.example.uinavegacion.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // ViewModel compartidos
    val cartViewModel: CartViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()

    // Obtener la ruta actual para saber qué pantalla está activa
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determinar si la ruta actual es de administrador
    val isAdminView = currentRoute?.startsWith("admin") == true

    // --- LÓGICA DE NAVEGACIÓN MEJORADA ---
    // Función genérica para navegar desde el menú
    val navigateAndCloseDrawer: (String) -> Unit = { route ->
        scope.launch { drawerState.close() } // Primero cierra el drawer
        navController.navigate(route) {
            launchSingleTop = true // Evita apilar la misma pantalla
            restoreState = true
        }
    }

    // Acción para cerrar sesión
    val onLogout: () -> Unit = {
        scope.launch { drawerState.close() }
        navController.navigate(Route.Login.path) {
            // Limpia todo el historial de navegación para que el usuario no pueda volver atrás
            popUpTo(0) { inclusive = true }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // --- MENÚ DE HAMBURGUESA TOTALMENTE FUNCIONAL ---
            AppDrawer(
                currentRoute = currentRoute,
                onNavigate = navigateAndCloseDrawer, // Usamos la función genérica
                onLogout = onLogout, // Pasamos la acción de logout
                isAdmin = isAdminView
            )
        }
    ) {
        Scaffold(
            topBar = {
                // Ocultamos la TopBar en las pantallas de autenticación y splash
                val showTopBar = currentRoute !in listOf(
                    Route.Splash.path, Route.Login.path, Route.Register.path, Route.ForgotPassword.path, Route.VerifyEmail.path
                ) && !isAdminView // Ocultar hamburguesa en admin
                if (showTopBar) {
                    AppTopBar(
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        onHome = { navController.navigate(Route.Home.path) },
                        onLogin = { navController.navigate(Route.Login.path) },
                        onRegister = { navController.navigate(Route.Register.path) },
                        onSearch = { query ->
                            searchViewModel.setQuery(query)
                            if (query.isNotBlank()) {
                                navController.navigate(Route.Games.path)
                            }
                        }
                    )
                }
            },
            bottomBar = {
                // Ocultamos la BottomBar donde no sea necesaria
                val showBottomBar = currentRoute in listOf(
                    Route.Home.path, Route.Games.path, Route.Library.path, Route.Cart.path, Route.Profile.path
                )
                if (showBottomBar) {
                    AppBottomBar(
                        currentRoute = currentRoute,
                        onHome = { navController.navigate(Route.Home.path) { launchSingleTop = true } },
                        onGames = { navController.navigate(Route.Games.path) { launchSingleTop = true } },
                        onCart = { navController.navigate(Route.Cart.path) { launchSingleTop = true } },
                        cartCount = cartViewModel.getTotalItems()
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                // --- PUNTO DE INICIO CAMBIADO A LOGIN ---
                startDestination = Route.Login.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Todas tus rutas se mantienen exactamente igual que antes
                // ===== Core =====
                composable(Route.Splash.path) { SplashScreen(navController) }
                composable(Route.Home.path) { HomeScreen(navController, cartViewModel) }

                // ===== Auth =====
                composable(Route.Login.path) {
                    LoginScreenVm(
                        // Cuando el login es exitoso, navegamos a Home y limpiamos el historial
                        onLoginOkNavigateHome = {
                            navController.navigate(Route.Home.path) {
                                popUpTo(Route.Login.path) { inclusive = true }
                            }
                        },
                        onGoRegister = { navController.navigate(Route.Register.path) },
                        navController = navController
                    )
                }
                composable(Route.Register.path) {
                    RegisterScreen(
                        nav = navController
                    )
                }
                composable(Route.ForgotPassword.path) { ForgotPasswordScreen(navController) }
                composable(
                    route = Route.VerifyEmail.path,
                    arguments = listOf(navArgument("email") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    })
                ) { backStack ->
                    val email = backStack.arguments?.getString("email").orEmpty()
                    VerifyEmailScreen(navController, email)
                }

                // ===== Perfil =====
                composable(Route.Profile.path) { ProfileScreen(navController) }
                composable(Route.ProfileEdit.path) { ProfileEditScreen(navController) }
                composable(Route.ChangePassword.path) { ChangePasswordScreen(navController) }

                // ===== Catálogo =====
                composable(Route.Games.path) { GamesScreen(navController, searchViewModel, cartViewModel) }
                composable(
                    route = Route.GameDetail.path,
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStack ->
                    val gameId = backStack.arguments?.getString("gameId").orEmpty()
                    GameDetailScreen(navController, gameId, cartViewModel)
                }
                composable(Route.Library.path) { LibraryScreen(navController) }

                // ===== Tienda / Órdenes =====
                composable(Route.Cart.path) { CartScreen(navController, cartViewModel) }
                composable(Route.Checkout.path) { CheckoutScreen(navController) }
                composable(Route.Orders.path) { OrdersScreen(navController) }
                composable(
                    route = Route.OrderDetail.path,
                    arguments = listOf(navArgument("orderId") { type = NavType.StringType })
                ) { backStack ->
                    val orderId = backStack.arguments?.getString("orderId").orEmpty()
                    OrderDetailScreen(navController, orderId)
                }

                // ===== Ajustes =====
                composable(Route.Settings.path) { SettingsScreen(navController) }
                composable(Route.CredentialsInfo.path) { CredentialsInfoScreen(navController) }

                // ===== Administrador =====
                composable(Route.AdminDashboard.path) { AdminDashboardScreen(navController) }
                composable(Route.AdminGames.path) { AdminGamesScreen(navController) }
                // <-- CORRECCIÓN 2: Se pasa explícitamente null al gameId para el modo "Añadir"
                composable(Route.AdminAddGame.path) { AdminAddGameScreen(navController, gameId = null) }
                composable(
                    route = Route.AdminEditGame.path,
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStack ->
                    val gameId = backStack.arguments?.getString("gameId").orEmpty()
                    // Se reutiliza la misma pantalla, pasando el ID para el modo "Editar"
                    AdminAddGameScreen(navController, gameId = gameId)
                }
                composable(Route.AdminUsers.path) { AdminUsersScreen(navController) }

                // ===== Estados / Errores =====
                composable(Route.NoConnection.path) { NoConnectionScreen(navController) }
                composable(Route.Maintenance.path) { MaintenanceScreen(navController) }
                composable(Route.NotFound.path) { NotFoundScreen(navController) }
            }
        }
    }
}
