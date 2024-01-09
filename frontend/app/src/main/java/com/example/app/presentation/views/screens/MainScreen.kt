package com.example.app.presentation.views.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app.R
import com.example.app.common.nav.BottomNavigationItem
import com.example.app.common.nav.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
    val bottomBarTabs = BottomNavigationItem.entries.toTypedArray()
    val bottomBarRoutes = bottomBarTabs.map { it.route }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(),
                title = {
                    Text(
                        text = "SavePlate",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController ) {
    val screens = listOf(
        BottomNavigationItem.ProductOverview,
        BottomNavigationItem.Account
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        containerColor = Color.LightGray,
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.AddItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            BadgedBox(
                badge = {
                    if (screen.badgeCount != null) {
                        Badge {
                            Text(text = screen.badgeCount.toString())
                        }
                    } else if (screen.hasNews) {
                        Badge()
                    }

                }) {
                Icon(
                    imageVector = if (currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true) {
                        ImageVector.vectorResource(id = screen.selectedIcon)
                    } else ImageVector.vectorResource(id = screen.unselectedIcon),
                    contentDescription = screen.title)
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}