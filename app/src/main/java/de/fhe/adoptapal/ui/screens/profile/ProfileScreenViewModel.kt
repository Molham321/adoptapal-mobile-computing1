package de.fhe.adoptapal.ui.screens.profile

import androidx.lifecycle.ViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen

class ProfileScreenViewModel(
    private val navigationManager: NavigationManager,
): ViewModel() {

    fun navigateToSettings() {
        navigationManager.navigate(Screen.Settings.navigationCommand())
    }
}