package es.voghdev.katallmandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import es.voghdev.katallmandroid.features.home.ui.HomeScreen
import es.voghdev.katallmandroid.navigation.AppNavigation
import es.voghdev.katallmandroid.ui.theme.KataLLMAndroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KataLLMAndroidTheme {
                AppNavigation { onNavigateToProfile, onNavigateToLlmDetail ->
                    HomeScreen(
                        onNavigateToProfile = onNavigateToProfile,
                        onNavigateToLlmDetail = onNavigateToLlmDetail,
                    )
                }
            }
        }
    }
}
