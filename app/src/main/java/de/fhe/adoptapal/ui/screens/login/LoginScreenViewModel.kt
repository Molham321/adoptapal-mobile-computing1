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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginScreenViewModel(
    private val getUserByEmailAsyncUseCase: GetUserByEmailAsync,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    var dbOp = mutableStateOf(AsyncOperation.undefined())

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            if (userEmail.isBlank() || userPassword.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("please enter Email and Password"))
            } else {
                getUserByEmailAsyncUseCase.invoke(userEmail).collect {
                    dbOp.value = it
                    if (it.status == AsyncOperationState.SUCCESS) {
                        val user = it.payload as User
                        // set logged in user credentials in local store
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

    fun navigateToRegister() {
        navigationManager.navigate(Screen.Register.navigationCommand())
    }

     fun navigateToProfile() {
        navigationManager.navigate(Screen.Profile.navigationCommand())
    }
}