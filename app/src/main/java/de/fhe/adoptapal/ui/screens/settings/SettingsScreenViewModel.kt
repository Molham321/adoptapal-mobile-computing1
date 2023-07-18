package de.fhe.adoptapal.ui.screens.settings

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetLatLongForAddress
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val updateUserAsyncUserCase: UpdateUserAsync,
    private val navigationManager: NavigationManager,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val getLatLongForAddress: GetLatLongForAddress
) : ViewModel() {

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)

    init {
        this.getUser()
    }

    fun getUser() {
        Log.i("Settings", "init")
        viewModelScope.launch {
            Log.i("Settings", "launching")
            getLoggedInUserFromDataStoreAndDatabase().collect {
                Log.i("Settings", "Collecting")
                if (it.status == AsyncOperationState.SUCCESS) {
                    Log.i("Settings", "found user with id: ${(it.payload as User).id}")
                    user.value = it.payload as User
                }
                if (it.status == AsyncOperationState.ERROR) {
                    Log.i("Settings", "Failed to load user")
                }
            }
        }
    }

    fun updateUser(updateUser: User) {
        viewModelScope.launch {
            // always get latLong on update if the address is not null
            if (updateUser.address != null) {
                getLatLongForAddress.invoke(updateUser.address!!).collect {
                    if (it.status == AsyncOperationState.SUCCESS) {
                        updateUser.address = it.payload as Address
                    }
                }
            }

            // update user
            updateUserAsyncUserCase(updateUser).collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    saveFeedbackFlow.emit(it)
                    navigationManager.navigate(GoBackDestination)
                }

                if (it.status == AsyncOperationState.ERROR) {
                    saveFeedbackFlow.emit(it)
                }
            }
        }
    }
}