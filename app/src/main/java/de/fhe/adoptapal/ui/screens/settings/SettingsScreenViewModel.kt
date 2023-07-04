package de.fhe.adoptapal.ui.screens.settings

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction.Companion.Go
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val updateUserAsyncUserCase: UpdateUserAsync,
    private val navigationManager: NavigationManager,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
): ViewModel() {

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)
    init {
        this.getUser()
    }

    private fun getUser() {
        Log.i("Settings", "init")
        viewModelScope.launch {
            Log.i("Settings", "launching")
            getLoggedInUserFromDataStoreAndDatabase().collect{
                Log.i("Settings", "Collecting")
                if(it.status == AsyncOperationState.SUCCESS) {
                    Log.i("Settings", "found user with id: ${(it.payload as User).id}")
                    user.value = it.payload as User
                }
                if(it.status == AsyncOperationState.ERROR) {
                    Log.i("Settings", "Failed to load user")
                }
            }
        }
    }

    fun updateUser(user: User) {
        Log.i("Settings", "update user with id: ${user.id}")
        viewModelScope.launch {
            updateUserAsyncUserCase(user).collect {

                if(it.status == AsyncOperationState.SUCCESS) {
                    saveFeedbackFlow.emit(it)
                    TODO("Go to different page?")
                }

                if(it.status == AsyncOperationState.ERROR) {
                    saveFeedbackFlow.emit(it)
                }
            }
        }
    }
}