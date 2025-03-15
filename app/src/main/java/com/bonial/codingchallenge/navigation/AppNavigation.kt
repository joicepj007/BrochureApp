package com.bonial.codingchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bonial.presentation.navigation.navigation.BrochuresRoute
import com.bonial.presentation.navigation.navigation.brochuresScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = BrochuresRoute
    ) {
        brochuresScreen()
    }
}