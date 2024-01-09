package com.example.app.common.nav

import com.example.app.R
import com.example.app.common.Screens

enum class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
) {
    ProductOverview(
        title = "ProductOverview",
        route = Screens.ProductOverviewScreen.route,
        selectedIcon = R.drawable.utensils_crossed_icon,
        unselectedIcon = R.drawable.utensils_icon,
        hasNews = false,
    ),

    Account(
        title = "Account",
        route = Screens.AccountScreen.route,
        selectedIcon = R.drawable.user_filled_icon,
        unselectedIcon = R.drawable.user_icon,
        hasNews = false,
    ),

}
