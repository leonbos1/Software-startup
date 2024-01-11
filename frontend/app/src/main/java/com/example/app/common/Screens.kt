package com.example.app.common

sealed class Screens(val route: String) {
    object MainScreen : Screens("main")
    object ProductOverviewScreen: Screens("productOverview")
    object ProductDetailsScreen: Screens("productDetails")
    object AccountScreen : Screens("account")
    object AddProductScreen : Screens("addProduct")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}