package es.voghdev.katallmandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.voghdev.katallmandroid.features.llm.ui.LLMDetailScreen
import es.voghdev.katallmandroid.features.profile.ui.ProfileScreen

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val LLM_DETAIL = "llm_detail/{index}"
}

@Composable
fun AppNavigation(
    homeContent: @Composable (onNavigateToProfile: () -> Unit, onNavigateToDetail: (Int) -> Unit) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            homeContent(
                { navController.navigate(Routes.PROFILE) },
                { index -> navController.navigate("llm_detail/$index") },
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Routes.LLM_DETAIL,
            arguments = listOf(navArgument("index") { type = NavType.IntType }),
        ) {
            LLMDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
