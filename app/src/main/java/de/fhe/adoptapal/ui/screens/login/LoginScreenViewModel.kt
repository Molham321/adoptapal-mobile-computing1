package de.fhe.adoptapal.ui.screens.login

import androidx.lifecycle.ViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen

class LoginScreenViewModel(
    private val navigationManager: NavigationManager,
) : ViewModel() {
    fun navigateToRegister() {
        navigationManager.navigate(Screen.Register.navigationCommand())
    }
}