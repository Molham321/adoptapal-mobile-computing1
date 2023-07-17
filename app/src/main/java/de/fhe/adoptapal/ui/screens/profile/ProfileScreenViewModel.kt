package de.fhe.adoptapal.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
) : ViewModel() {

    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)

    init {
//        Log.i("Profile", "init class")
        this.getUser()
    }

    fun getUser() {
//        Log.i("Profile", "init")
        viewModelScope.launch {
//            Log.i("Profile", "launching")
            getLoggedInUserFromDataStoreAndDatabase().collect {
//                Log.i("Profile", "Collecting")
                if (it.status == AsyncOperationState.SUCCESS) {
//                    Log.i("Profile", "found user with id: ${(it.payload as User).id}")
                    user.value = it.payload as User
                }
                if (it.status == AsyncOperationState.ERROR) {
//                    Log.i("Profile", "Failed to load user")
                }
            }
        }
    }

    fun reload() {
        this.getUser()
    }

    fun navigateToSettings() {
        navigationManager.navigate(Screen.Settings.navigationCommand())
    }
}