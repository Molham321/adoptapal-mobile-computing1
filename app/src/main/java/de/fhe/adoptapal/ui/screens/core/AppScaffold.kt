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
import org.koin.androidx.compose.getKoin

val LocalScaffoldState =
    staticCompositionLocalOf<ScaffoldState> { error("no scaffolded state set") }

@Composable
fun AppScaffold() {
    val scaffoldState = rememberScaffoldState()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Undefined) }

    val navigationManager by getKoin().inject<NavigationManager>()

    CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { AppBar(currentScreen) },
            bottomBar = { BottomBar(navigationManager, currentScreen) }
        ) { innerPadding ->
            AppNavigationHost(
                navigationManager,
                onNavigation = {
                    currentScreen = it
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}