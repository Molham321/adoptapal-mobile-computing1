package de.fhe.adoptapal.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
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

    var filteredAnimals = mutableStateOf(emptyList<Animal>())

    var showFilterDialog by mutableStateOf(false)

    var initialAgeFrom by mutableStateOf(0)
    var initialAgeTo by mutableStateOf(0)
    var initialSelectedGender by mutableStateOf("Alle")
    var initialColor by mutableStateOf("")
    var initialWeightFrom by mutableStateOf(0)
    var initialWeightTo by mutableStateOf(0)
    var initialCity by mutableStateOf("")
    var initialBreed by mutableStateOf(TextFieldValue())
    var initialArt by mutableStateOf(TextFieldValue())

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
                "${age.days} Tage"
            } else {
                "${age.months} Monate"
            }
        } else {
            "${age.years} Jahre"
        }
    }

    fun getFilteredAnimals(
        animalList: List<Animal>,
        filterText: String
    ): List<Animal> {
        return animalList.filter { animal ->
            // Filter based on the search text (filterText)
            val searchTextMatch = animal.name.contains(filterText, ignoreCase = true) ||
                    animal.description.contains(filterText, ignoreCase = true) ||
                    animal.weight.toString().contains(filterText, ignoreCase = true) ||
                    animal.animalCategory.name.contains(filterText, ignoreCase = true) ||
                    animal.supplier.address?.city?.contains(filterText, ignoreCase = true) == true

            searchTextMatch
        }
    }


    fun updateAnimalList(
        ageFrom: Int?,
        ageTo: Int?,
        isMale: Boolean?,
        color: String?,
        weightFrom: Int,
        weightTo: Int,
        city: String?,
    ): List<Animal> {
        return animalList.value.filter { animal ->
            val ageCondition =
                (ageFrom == null || animal.birthday.plusYears(ageFrom.toLong()) >= LocalDate.now()) &&
                        (ageTo == null || animal.birthday.plusYears(ageTo.toLong()) <= LocalDate.now())
            val maleCondition = isMale == null || animal.isMale == isMale
            val colorCondition =
                color.isNullOrBlank() || animal.color.name.contains(color, ignoreCase = true)
            val weightCondition = (weightFrom == 0 || animal.weight >= weightFrom) &&
                    (weightTo == 0 || animal.weight <= weightTo)
            val cityCondition = city.isNullOrBlank() || animal.supplier.address?.city?.contains(
                city,
                ignoreCase = true
            ) == true

            ageCondition && maleCondition && colorCondition && weightCondition && cityCondition
        }
    }


    fun updateFilteredAnimalList(filteredAnimals: List<Animal>) {
        this.filteredAnimals.value = filteredAnimals
    }

    fun resetFiltersAndShowAllAnimals() {
        filteredAnimals.value = animalList.value
    }

    fun resetFilterValues() {
        // Hier werden die Filterwerte zurückgesetzt
        initialAgeFrom = 0
        initialAgeTo = 0
        initialSelectedGender = "Alle"
        initialColor = ""
        initialWeightFrom = 0
        initialWeightTo = 0
        initialCity = ""
        initialBreed = TextFieldValue()
        initialArt = TextFieldValue()
    }

    fun openFilterDialog() {
        showFilterDialog = true
    }

    fun navigateToAddAnimal() {
        navigationManager.navigate(Screen.AddAnimal.navigationCommand())
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }

    fun logout() { // TODO fix or delete
        viewModelScope.launch {
            setLoggedInUserInDataStore(0)
            user.value = null
        }
    }

}