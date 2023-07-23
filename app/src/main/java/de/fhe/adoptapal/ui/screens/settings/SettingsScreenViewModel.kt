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
                } else {
                    Log.i("Error!", "das hat nicht funktioniert!")
                }

                if (it.status == AsyncOperationState.ERROR) {
                    saveFeedbackFlow.emit(it)
                }
            }
        }
    }

    fun validateName(name: String): Boolean {
        val namePattern = "^[a-zA-ZäöüÄÖÜß\\s-]+$"
        return name.matches(namePattern.toRegex())
    }

    fun validateEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(emailPattern.toRegex())
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^(\\+49|0)(\\d{3,4})[ -]?(\\d{3,})([ -]?\\d{1,5})?\$"
        return phoneNumber.matches(phonePattern.toRegex())
    }

    fun validateStreet(street: String): Boolean {
        val streetPattern = "^[a-zA-Z0-9äöüÄÖÜß\\s\\-.,]+$"
        return street.matches(streetPattern.toRegex())
    }

    fun validateCity(city: String): Boolean {
        val cityPattern = "^[a-zA-ZäöüÄÖÜß\\s]+$"
        return city.matches(cityPattern.toRegex())
    }

    fun validateHouseNumber(houseNumber: String): Boolean {
        val houseNumberPattern = "^[a-zA-Z0-9\\-]+$"
        return houseNumber.matches(houseNumberPattern.toRegex())
    }

    fun validateZip(zip: String): Boolean {
        val zipPattern = "^[0-9]{5}\$"
        return zip.matches(zipPattern.toRegex())
    }
}