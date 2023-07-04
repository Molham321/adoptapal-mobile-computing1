package de.fhe.adoptapal.ui.screens.core

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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