package de.fhe.adoptapal.ui.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for the Login screen.
 *
 * @param getUserByEmailAsyncUseCase Use case to get user details by email.
 * @param setLoggedInUserInDataStore Use case to set the logged-in user in data store.
 * @param navigationManager Manager for screen navigation.
 */
class LoginScreenViewModel(
    private val getUserByEmailAsyncUseCase: GetUserByEmailAsync,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    // State for database operation status
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    // State for saving feedback flow
    var saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    /**
     * Function to perform user login.
     *
     * @param userEmail The user's email.
     * @param userPassword The user's password.
     */
    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            if (userEmail.isBlank() || userPassword.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Please enter Email and Password"))
            } else {
                getUserByEmailAsyncUseCase.invoke(userEmail).collect { result ->
                    dbOp.value = result
                    if (result.status == AsyncOperationState.SUCCESS) {
                        val user = result.payload as User
                        // Set logged-in user credentials in local store
                        setLoggedInUserInDataStore(user.id).collect()
                        Log.i("Login", "Logged in user with userId: ${user.id}")
                        navigateToProfile()
                    } else {
                        saveFeedbackFlow.emit(AsyncOperation.error("Email or Password not correct"))
                    }
                }
            }
        }
    }

    // Validation functions

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
     * Function to validate the password length.
     *
     * @param password The password to be validated.
     * @return True if the password length is valid, false otherwise.
     */
    fun validatePassword(password: String): Boolean {
        return password.length >= 3
    }

    /**
     * Function to navigate to the registration screen.
     */
    fun navigateToRegister() {
        navigationManager.navigate(Screen.Register.navigationCommand())
    }

    /**
     * Function to navigate to the user's profile screen.
     */
    fun navigateToProfile() {
        navigationManager.navigate(Screen.Profile.navigationCommand())
    }
}