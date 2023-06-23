package de.fhe.adoptapal.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val GetUserByEmailAsyncUseCase: GetUserByEmailAsync,
    private val navigationManager: NavigationManager,
) : ViewModel() {
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {

            if (userEmail.isBlank() || userPassword.isBlank() ) {
                saveFeedbackFlow.emit(AsyncOperation.error("please enter Email and Password"))
            } else {
                val user = GetUserByEmailAsyncUseCase(userEmail)
                if(user != null && userPassword === "123")
                    LoginManager.loggedIn = true
                    navigateToProfile()
                }
            }
        }
    fun navigateToRegister() {
        navigationManager.navigate(Screen.Register.navigationCommand())
    }
    fun navigateToProfile() {
        navigationManager.navigate(Screen.Profile.navigationCommand())
    }
}

object LoginManager {
    var loggedIn: Boolean = false
}