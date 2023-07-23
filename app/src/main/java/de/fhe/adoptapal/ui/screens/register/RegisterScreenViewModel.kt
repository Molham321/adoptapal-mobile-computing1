package de.fhe.adoptapal.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val insertUserAsyncUseCase: InsertUserAsync,
    val navigationManager: NavigationManager,
) : ViewModel() {

    var saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun addUser(userName: String, userEmail: String, userPhoneNumber: String) {
        viewModelScope.launch {

            if (userName.isBlank() || userEmail.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Name oder Email fehlt"))
            } else {
                val newUser = User(userName, userEmail, userPhoneNumber, null)
                saveFeedbackFlow.emit(AsyncOperation.saving("Saving user now!"))
                insertUserAsyncUseCase(newUser).collect {
                    if (it.status == AsyncOperationState.SUCCESS) {
                        saveFeedbackFlow.emit(it)
                        navigationManager.navigate(GoBackDestination)
                    }
                    if (it.status == AsyncOperationState.ERROR) {
                        Log.i("RegisterScreenVW", it.message)
                        saveFeedbackFlow.emit(it)
                    }
                }
            }
        }
    }

    fun validateName(name: String): Boolean {
        val namePattern = "^[a-zA-ZäöüÄÖÜß\\s-]+$"
        return name.matches(namePattern.toRegex())
    }

    fun validateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^(\\+49|0)(\\d{3,4})[ -]?(\\d{3,})([ -]?\\d{1,5})?\$"
        return phoneNumber.matches(phonePattern.toRegex()) || phoneNumber == ""
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 3
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }
}