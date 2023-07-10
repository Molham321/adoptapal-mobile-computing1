package de.fhe.adoptapal.ui.screens.home

//import de.fhe.adoptapal.model.Animal
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class HomeScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllAnimals: GetAllAnimals,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
) : ViewModel() {
    var animalList = mutableStateOf(emptyList<Animal>())

    //    var animalAge = mutableStateOf(0)
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)

    init {
        this.getLoggedInUser()
        this.getAnimalsFromDb()
    }

    private fun getAnimalsFromDb() {
        viewModelScope.launch {
            getAllAnimals().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    private fun getLoggedInUser() {
        Log.i("Profile", "init")
        viewModelScope.launch {
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

    fun refreshUser() {
        this.getLoggedInUser()
    }


    fun getAge(birthday: LocalDate): String {
        val currentDate = LocalDate.now()
        val age = Period.between(birthday, currentDate)
        return "${age.years} years"
    }

    fun navigateToAddAnimal() {
        navigationManager.navigate(Screen.AddAnimal.navigationCommand())
    }

    fun navigateToSearch() {
        navigationManager.navigate(Screen.Search.navigationCommand())
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }

    fun logout(){
        // TODO remove datastore value
    }

}