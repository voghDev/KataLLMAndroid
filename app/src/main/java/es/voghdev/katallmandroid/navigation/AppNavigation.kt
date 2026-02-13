package es.voghdev.katallmandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.voghdev.katallmandroid.features.profile.ui.ProfileScreen

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation(homeContent: @Composable (onNavigateToProfile: () -> Unit) -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            homeContent { navController.navigate(Routes.PROFILE) }
        }
        composable(Routes.PROFILE) {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
