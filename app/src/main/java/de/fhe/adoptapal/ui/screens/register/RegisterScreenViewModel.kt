package de.fhe.adoptapal.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private  val insertUserAsyncUseCase: InsertUserAsync,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun addUser(userName: String, userEmail: String, userPhoneNumber: String) {
        viewModelScope.launch {

            if (userName.isBlank() || userEmail.isBlank() || userPhoneNumber.isBlank() ) {
                saveFeedbackFlow.emit(AsyncOperation.error("User name, email and phone are missing"))
            } else {
                val newUser = User(userName, userEmail, userPhoneNumber, null)

                insertUserAsyncUseCase(newUser).collect {
                    saveFeedbackFlow.emit(it)
                    navigateToLogin()
                }
            }
        }
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }
}