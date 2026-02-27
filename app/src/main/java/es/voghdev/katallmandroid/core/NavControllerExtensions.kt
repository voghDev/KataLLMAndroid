package es.voghdev.katallmandroid.core

import androidx.navigation.NavController

fun NavController.popBackStack(ifCurrentRoute: String) {
    if (currentDestination?.route == ifCurrentRoute) {
        popBackStack()
    }
}
