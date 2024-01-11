package com.example.app.common.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app.common.Screens
import com.example.app.presentation.views.screens.ProductDetailsScreen
import com.example.app.presentation.views.screens.ProductOverviewScreen
import com.example.app.presentation.views.screens.AccountScreen
import com.example.app.presentation.views.screens.AddProductScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.ProductOverviewScreen.route) {
        composable(route = Screens.ProductOverviewScreen.route) {
            ProductOverviewScreen(navController)
        }
        composable(route = Screens.ProductOverviewScreen.route + "/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    nullable = false
                    type = NavType.StringType
                })
        ) { entry ->
            ProductDetailsScreen(navController, entry.arguments?.getString("productId"))
        }
        composable(route = Screens.AccountScreen.route) {
            AccountScreen(navController)
        }
        composable(route = Screens.AddProductScreen.route) {
            AddProductScreen(navController)
        }
    }
}