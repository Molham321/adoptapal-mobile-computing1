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
import de.fhe.adoptapal.ui.screens.home.LOGTAG
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

    private fun getUser() {
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
        Log.i("Settings", "update user with id: ${updateUser.id}")
        viewModelScope.launch {

//            // get latLong for address if the address is new or has changed
//            // do if updateUser has address and user has not -> then there is a new address
//            //or
//            // do if updateUser and user have addresses AND they are different
//            val addressIsNew = (updateUser.address != null && user.value != null && user.value!!.address == null)
//            val addressIsDifferent = (updateUser.address != null && user.value != null && user.value!!.address != null &&
//                    !updateUser.address!!.simpleEquals(user.value!!.address))
//           if(addressIsNew || addressIsDifferent){

            // always get latLong on update
            if ( true ) {

                getLatLongForAddress.invoke(updateUser.address!!).collect {
                    Log.i(LOGTAG, "start reqeust")
                    if (it.status == AsyncOperationState.SUCCESS) {

                        updateUser.address = it.payload as Address
                        Log.i(
                            LOGTAG,
                            "Lat: ${updateUser.address!!.latitude} Long:${updateUser.address!!.longitude}"
                        )

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
}