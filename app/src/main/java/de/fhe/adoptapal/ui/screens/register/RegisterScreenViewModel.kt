package de.fhe.adoptapal.ui.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import java.util.logging.Handler
import kotlin.concurrent.schedule

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
                saveFeedbackFlow.emit( AsyncOperation.saving("Saving user now!"))
                insertUserAsyncUseCase(newUser).collect {
                    if(it.status == AsyncOperationState.SUCCESS) {
                        saveFeedbackFlow.emit(it)
                        navigateToLogin()
                    }
                    if(it.status == AsyncOperationState.ERROR) {
                        Log.i("RegisterScreenVW", it.message)
                        saveFeedbackFlow.emit(it)
                    }
                }
            }
        }
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }
}