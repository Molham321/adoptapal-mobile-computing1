package de.fhe.adoptapal.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val GetUserByEmailAsyncUseCase: GetUserByEmailAsync,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    companion object {
        var loggedIn: Boolean = false
        var user = mutableStateOf<User?>(null)
    }


    var dbOp = mutableStateOf(AsyncOperation.undefined())

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            if (userEmail.isBlank() || userPassword.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("please enter Email and Password"))
            } else {
                GetUserByEmailAsyncUseCase.invoke(userEmail).collect {
                    dbOp.value = it
                    if (it.status == AsyncOperationState.SUCCESS) {
                        user.value = it.payload as User
                        loggedIn = true
                        navigateToProfile()
                    } else {
                        saveFeedbackFlow.emit(AsyncOperation.error("Email or Password not correct"))
                    }
                }
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