package com.bonial.presentation.navigation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bonial.presentation.navigation.brochurelist.BrochureListScreen
import kotlinx.serialization.Serializable

@Serializable
object BrochuresRoute

fun NavGraphBuilder.brochuresScreen() {
    composable<BrochuresRoute>() {
        BrochureListScreen()
    }
}