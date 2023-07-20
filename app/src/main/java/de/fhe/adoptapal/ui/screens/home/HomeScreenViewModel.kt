package de.fhe.adoptapal.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.Period

class HomeScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllAnimals: GetAllAnimals,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore
    ) : ViewModel() {

    var animalList = mutableStateOf(emptyList<Animal>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)

    init {
        this.getLoggedInUser()
        this.getAnimalsFromDb()
    }

    fun getAnimalsFromDb() {
        viewModelScope.launch {
            getAllAnimals().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            getLoggedInUserFromDataStoreAndDatabase().collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    user.value = it.payload as User
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

        return if (age.years < 1) {
            if (age.months < 1) {
                "${age.days} days"
            } else {
                "${age.months} months"
            }
        } else {
            "${age.years} years"
        }
    }

    fun getFilteredAnimals(filterText: String, selectedFilter: String?): List<Animal> {
        return animalList.value.filter { animal ->
            (animal.name.contains(filterText, ignoreCase = true) ||
                    animal.description.contains(filterText, ignoreCase = true) ||
                    animal.weight.toString().contains(filterText, ignoreCase = true) ||
                    animal.animalCategory.name.contains(filterText, ignoreCase = true) ||
                    animal.supplier.address?.city?.contains(filterText, ignoreCase = true) == true
                    )  &&
                    (selectedFilter == null ||
                            selectedFilter == "All" ||
                            (selectedFilter == "Male" && animal.isMale) ||
                            (selectedFilter == "Female" && !animal.isMale))
        }
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
        viewModelScope.launch {
            setLoggedInUserInDataStore(0)
            user.value = null
        }
    }

}