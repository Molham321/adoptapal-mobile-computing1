package de.fhe.adoptapal.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllFavoriteAnimalsAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val getAllFavoriteAnimalsAsync: GetAllFavoriteAnimalsAsync,
    private val getUserAnimalsAsync: GetUserAnimalsAsync
) : ViewModel() {

    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)
    var favoriteAnimalList = mutableStateOf(emptyList<Animal>())
    var userAnimalList = mutableStateOf(emptyList<Animal>())


    init {
        Log.i("Profile", "init class")
        this.getUser()
        this.getFavoriteAnimalsFromDb()
        if(user.value != null) {
            this.getUserAnimalsFromDb(user.value!!.id)
        }
    }

    fun getUser() {
        Log.i("Profile", "init")
        runBlocking {
            Log.i("Profile", "launching")
            getLoggedInUserFromDataStoreAndDatabase().collect {
                Log.i("Profile", "Collecting")
                if (it.status == AsyncOperationState.SUCCESS) {
                    Log.i("Profile", "found user with id: ${(it.payload as User).id}")
                    user.value = it.payload as User
                }
                if (it.status == AsyncOperationState.ERROR) {
                    Log.i("Profile", "Failed to load user")
                }
            }
        }
    }

    fun reload() {
        viewModelScope.launch {
            getUser()
        }
        // this.getUser()
    }

    fun getFavoriteAnimalsFromDb() {
        viewModelScope.launch {
            getAllFavoriteAnimalsAsync().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    favoriteAnimalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    fun getUserAnimalsFromDb(id: Long) {
        viewModelScope.launch {
            getUserAnimalsAsync(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    userAnimalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    fun navigateToSettings() {
        navigationManager.navigate(Screen.Settings.navigationCommand())
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }
}