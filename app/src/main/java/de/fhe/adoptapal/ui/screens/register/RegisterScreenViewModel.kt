package de.fhe.adoptapal.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for the Register screen.
 *
 * @param insertUserAsyncUseCase Use case to insert a new user.
 * @param navigationManager Manager for screen navigation.
 */
class RegisterScreenViewModel(
    private val insertUserAsyncUseCase: InsertUserAsync,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    val navigationManager: NavigationManager,
) : ViewModel() {

    // State for saving feedback flow
    var saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    /**
     * Function to add a new user.
     *
     * @param userName The user's name.
     * @param userEmail The user's email.
     */
    fun addUser(userName: String, userEmail: String) {
        viewModelScope.launch {

            if (userName.isBlank() || userEmail.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Name or Email missing"))
            } else {
                val newUser = User(userName, userEmail, null, null)
                saveFeedbackFlow.emit(AsyncOperation.saving("Saving user now!"))
                insertUserAsyncUseCase(newUser).collect { result ->
                    if (result.status == AsyncOperationState.SUCCESS) {
                        val userID = result.payload as Long
                        setLoggedInUserInDataStore(userID).collect()
                        navigateToProfile()
                    }
                    if (result.status == AsyncOperationState.ERROR) {
                        Log.i("RegisterScreenVW", result.message)
                        saveFeedbackFlow.emit(result)
                    }
                }
            }
        }
    }

    /**
     * Function to navigate to the user's profile screen.
     */
    private fun navigateToProfile() {
        navigationManager.navigate(Screen.Profile.navigationCommand())
    }

    // Validation functions

    /**
     * Function to validate the format of a name.
     *
     * @param name The name to be validated.
     * @return True if the name format is valid, false otherwise.
     */
    fun validateName(name: String): Boolean {
        val namePattern = "^[a-zA-ZäöüÄÖÜß\\s-]+$"
        return name.matches(namePattern.toRegex())
    }

    /**
     * Function to validate the format of an email address.
     *
     * @param email The email address to be validated.
     * @return True if the email format is valid, false otherwise.
     */
    fun validateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    /**
     * Function to validate the format of a phone number.
     *
     * @param phoneNumber The phone number to be validated.
     * @return True if the phone number format is valid, false otherwise.
     */
    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^(\\+49|0)(\\d{3,4})[ -]?(\\d{3,})([ -]?\\d{1,5})?\$"
        return phoneNumber.matches(phonePattern.toRegex()) || phoneNumber == ""
    }

    /**
     * Function to validate the password length.
     *
     * @param password The password to be validated.
     * @return True if the password length is valid, false otherwise.
     */
    fun validatePassword(password: String): Boolean {
        return password.length >= 3
    }

    /**
     * Function to validate the confirmation password.
     *
     * @param password The password to be compared.
     * @param confirmPassword The confirmation password to be compared.
     * @return True if the passwords match, false otherwise.
     */
    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * Function to navigate to the login screen.
     */
    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }
}