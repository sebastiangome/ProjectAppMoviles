package com.example.uinavegacion.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import kotlinx.coroutines.launch

// Rutas (sealed class)
import com.example.uinavegacion.navigation.Route

// TopBar + Drawer
import com.example.uinavegacion.ui.components.AppTopBar
import com.example.uinavegacion.ui.components.AppDrawer
import com.example.uinavegacion.ui.components.defaultDrawerItems

// ===== Pantallas =====
// Core
import com.example.uinavegacion.ui.screen.SplashScreen
import com.example.uinavegacion.ui.screen.OnboardingScreen
import com.example.uinavegacion.ui.screen.HomeScreen
// Auth
import com.example.uinavegacion.ui.screen.LoginScreen
import com.example.uinavegacion.ui.screen.RegisterScreen
import com.example.uinavegacion.ui.screen.ForgotPasswordScreen
import com.example.uinavegacion.ui.screen.VerifyEmailScreen
// Perfil
import com.example.uinavegacion.ui.screen.ProfileScreen
import com.example.uinavegacion.ui.screen.ProfileEditScreen
import com.example.uinavegacion.ui.screen.ChangePasswordScreen
// Catálogo
import com.example.uinavegacion.ui.screen.GamesScreen
import com.example.uinavegacion.ui.screen.GameDetailScreen
import com.example.uinavegacion.ui.screen.GameReviewsScreen
import com.example.uinavegacion.ui.screen.NewReviewScreen
import com.example.uinavegacion.ui.screen.LibraryScreen
import com.example.uinavegacion.ui.screen.LicensesScreen
// Tienda / Órdenes
import com.example.uinavegacion.ui.screen.CartScreen
import com.example.uinavegacion.ui.screen.CheckoutScreen
import com.example.uinavegacion.ui.screen.OrdersScreen
import com.example.uinavegacion.ui.screen.OrderDetailScreen
// Soporte / Notificaciones
import com.example.uinavegacion.ui.screen.NotificationsScreen
import com.example.uinavegacion.ui.screen.HelpScreen
import com.example.uinavegacion.ui.screen.FeedbackScreen
// Ajustes
import com.example.uinavegacion.ui.screen.SettingsScreen
import com.example.uinavegacion.ui.screen.SettingsLanguageScreen
import com.example.uinavegacion.ui.screen.SettingsThemeScreen
import com.example.uinavegacion.ui.screen.SettingsPrivacyScreen
import com.example.uinavegacion.ui.screen.AboutScreen
import com.example.uinavegacion.ui.screen.TermsScreen
import com.example.uinavegacion.ui.screen.PrivacyScreen
// Estados
import com.example.uinavegacion.ui.screen.NoConnectionScreen
import com.example.uinavegacion.ui.screen.MaintenanceScreen
import com.example.uinavegacion.ui.screen.NotFoundScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Helpers de navegación usados por TopBar/Drawer/Pantallas
    val goHome: () -> Unit       = { navController.navigate(Route.Home.path) }
    val goLogin: () -> Unit      = { navController.navigate(Route.Login.path) }
    val goRegister: () -> Unit   = { navController.navigate(Route.Register.path) }
    val goGames: () -> Unit      = { navController.navigate(Route.Games.path) }
    val goLibrary: () -> Unit    = { navController.navigate(Route.Library.path) }
    val goProfile: () -> Unit    = { navController.navigate(Route.Profile.path) }
    val goCart: () -> Unit       = { navController.navigate(Route.Cart.path) }
    val goOrders: () -> Unit     = { navController.navigate(Route.Orders.path) }
    val goSettings: () -> Unit   = { navController.navigate(Route.Settings.path) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                items = defaultDrawerItems(
                    onHome = {
                        scope.launch { drawerState.close() }
                        goHome()
                    },
                    onLogin = {
                        scope.launch { drawerState.close() }
                        goLogin()
                    },
                    onRegister = {
                        scope.launch { drawerState.close() }
                        goRegister()
                    }
                )
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    onOpenDrawer = { scope.launch { drawerState.open() } },
                    onHome = goHome,
                    onLogin = goLogin,
                    onRegister = goRegister
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Splash.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                // ===== Core =====
                composable(Route.Splash.path) { SplashScreen(navController) }
                composable(Route.Onboarding.path) { OnboardingScreen(navController) }
                composable(Route.Home.path) { HomeScreen(navController) }

                // ===== Auth =====
                composable(Route.Login.path) {
                    LoginScreen(
                        nav = navController
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
                composable(Route.Games.path) { GamesScreen(navController) }
                composable(
                    route = Route.GameDetail.path,
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStack ->
                    val gameId = backStack.arguments?.getString("gameId").orEmpty()
                    GameDetailScreen(navController, gameId)
                }
                composable(
                    route = Route.GameReviews.path,
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStack ->
                    val gameId = backStack.arguments?.getString("gameId").orEmpty()
                    GameReviewsScreen(navController, gameId)
                }
                composable(
                    route = Route.NewReview.path,
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStack ->
                    val gameId = backStack.arguments?.getString("gameId").orEmpty()
                    NewReviewScreen(navController, gameId)
                }
                composable(Route.Library.path) { LibraryScreen(navController) }
                composable(Route.Licenses.path) { LicensesScreen(navController) }

                // ===== Tienda / Órdenes =====
                composable(Route.Cart.path) { CartScreen(navController) }
                composable(Route.Checkout.path) { CheckoutScreen(navController) }
                composable(Route.Orders.path) { OrdersScreen(navController) }
                composable(
                    route = Route.OrderDetail.path,
                    arguments = listOf(navArgument("orderId") { type = NavType.StringType })
                ) { backStack ->
                    val orderId = backStack.arguments?.getString("orderId").orEmpty()
                    OrderDetailScreen(navController, orderId)
                }

                // ===== Notificaciones / Soporte =====
                composable(Route.Notifications.path) { NotificationsScreen(navController) }
                composable(Route.Help.path) { HelpScreen(navController) }
                composable(Route.Feedback.path) { FeedbackScreen(navController) }

                // ===== Ajustes =====
                composable(Route.Settings.path) { SettingsScreen(navController) }
                composable(Route.SettingsLanguage.path) { SettingsLanguageScreen(navController) }
                composable(Route.SettingsTheme.path) { SettingsThemeScreen(navController) }
                composable(Route.SettingsPrivacy.path) { SettingsPrivacyScreen(navController) }
                composable(Route.About.path) { AboutScreen(navController) }
                composable(Route.Terms.path) { TermsScreen(navController) }
                composable(Route.Privacy.path) { PrivacyScreen(navController) }

                // ===== Estados / Errores =====
                composable(Route.NoConnection.path) { NoConnectionScreen(navController) }
                composable(Route.Maintenance.path) { MaintenanceScreen(navController) }
                composable(Route.NotFound.path) { NotFoundScreen(navController) }
            }
        }
    }
}
