package de.fhe.adoptapal.ui.screens.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getKoin

val LocalScaffoldState =
    staticCompositionLocalOf<ScaffoldState> { error("no scaffolded state set") }
/**
 * Composable function responsible for setting up the main scaffold layout of the app.
 * This includes the top and bottom app bars, navigation host, and scaffold state.
 */
@Composable
fun AppScaffold() {
    val scaffoldState = rememberScaffoldState()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Undefined) }

    val navigationManager by getKoin().inject<NavigationManager>()
    val navController = rememberNavController()

    CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { AppBar(currentScreen, navController) },
            bottomBar = { BottomBar(navigationManager, currentScreen) }
        ) { innerPadding ->
            AppNavigationHost(
                navigationManager,
                navController,
                onNavigation = {
                    currentScreen = it
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}