package de.fhe.adoptapal.ui.screens.core

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * Composable function responsible for displaying the bottom navigation bar in the app.
 * It dynamically generates BottomNavigationItems based on the available root screens.
 *
 * @param navigationManager The NavigationManager instance to handle navigation commands.
 * @param currentScreen The currently displayed screen, used to highlight the selected item.
 */
@Composable
fun BottomBar(
    navigationManager: NavigationManager,
    currentScreen: Screen
) {
    BottomNavigation {
        for (screen in RootScreens) {
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentScreen == screen,
                onClick = {
                    navigationManager.navigate(screen.navigationCommand())
                }
            )
        }
    }
}