package de.fhe.adoptapal.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetAllColors
import de.fhe.adoptapal.domain.GetLatLongForLocationString
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.Period

/**
 * ViewModel class for the HomeScreen.
 *
 * @param navigationManager The navigation manager for handling screen transitions.
 * @param getAllAnimals Use case for retrieving animals from the database.
 * @param getLoggedInUserFromDataStoreAndDatabase Use case for retrieving logged-in user data.
 * @param setLoggedInUserInDataStore Use case for setting logged-in user data.
 * @param getAllColors Use case for retrieving animal colors from the database.
 * @param getLatLongForLocationString Use case for getting latitude and longitude from a location string.
 * @param getAllAnimalCategories Use case for retrieving animal categories from the database.
 */
class HomeScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllAnimals: GetAllAnimals,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    private val getAllColors: GetAllColors,
    private val getLatLongForLocationString: GetLatLongForLocationString,
    private val getAllAnimalCategories: GetAllAnimalCategories,
) : ViewModel() {

    // Mutable state properties for UI components
    var showFilterDialog by mutableStateOf(false)
    var initialAgeFrom by mutableStateOf(0)
    var initialAgeTo by mutableStateOf(0)
    var initialWeightFrom by mutableStateOf(0)
    var initialWeightTo by mutableStateOf(0)
    var initialCity by mutableStateOf("")
    var initialName by mutableStateOf("")
    var initialDescription by mutableStateOf("")
    var initialColor by mutableStateOf("")
    var initialArt by mutableStateOf("")
    var initialSelectedGender by mutableStateOf("Alle")
    private var initialBreed by mutableStateOf(TextFieldValue())
    var initialDistance by mutableStateOf(0)

    var animalColorList = mutableStateOf(emptyList<Color>())
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalList = mutableStateOf(emptyList<Animal>())
    var filteredAnimals = mutableStateOf(emptyList<Animal>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)
    private var filterLocation by mutableStateOf<Location?>(null)

    /**
     * Initialize the ViewModel by fetching initial data.
     */
    init {
        this.getLoggedInUser()
        this.getAnimalsFromDb()
        this.getColorsFromDb()
        this.getAnimalCategoriesFromDb()
    }

    /**
     * Fetch animals from the database.
     */
    fun getAnimalsFromDb() {
        viewModelScope.launch {
            getAllAnimals().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                    filteredAnimals.value = animalList.value
                }
            }
        }
    }

    /**
     * Fetch animal colors from the database.
     */
    private fun getColorsFromDb() {
        viewModelScope.launch {
            getAllColors().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalColorList.value = it.payload as List<Color>
                }
            }
        }
    }

    /**
     * Fetch animal categories from the database.
     */
    private fun getAnimalCategoriesFromDb() {
        viewModelScope.launch {
            getAllAnimalCategories().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalCategoryList.value = it.payload as List<AnimalCategory>
                }
            }
        }
    }

    /**
     * Fetch the logged-in user data from the data store and database.
     */
    fun getLoggedInUser() {
        viewModelScope.launch {
            getLoggedInUserFromDataStoreAndDatabase().collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    user.value = it.payload as User
                }
            }
        }
    }

    /**
     * Refresh the logged-in user data.
     */
    fun refreshUser() {
        this.getLoggedInUser()
    }

    /**
     * Update the filtered animal list based on filter criteria.
     */
    fun updateAnimalList(
        ageFrom: Int,
        ageTo: Int,
        isMale: Boolean?,
        color: String?,
        art: String?,
        weightFrom: Int,
        weightTo: Int,
        city: String?,       
        name: String?,
        description: String?,

        distance: Int
    ): List<Animal> {
        if (city != null) {
            this.getLocationByString(city)
        }
        return animalList.value.filter { animal ->

            // check if animal is within selected age
            // if both ages are 0 then all animals are selected
            val animalAge = Period.between(animal.birthday, LocalDate.now()).years
            val bothAgesZero = ageFrom == 0 && ageTo == 0
            val ageCondition = if (bothAgesZero) (true) else (animalAge in ageFrom..ageTo)

            // check the selected gender -> null is all genders
            val genderCondition = isMale == null || animal.isMale == isMale

            // check the selected color
            val colorCondition = color.isNullOrBlank() || animal.color.name.contains(color, ignoreCase = true)

            // check the selected animalCategory
            val animalCategoryCondition   = art.isNullOrBlank()   || animal.animalCategory.name.contains(art, ignoreCase = true)


            // check if animal weight is within selected weight
            val weightIsZero = weightTo == 0 && weightFrom == 0
            val weightCondition = if (weightIsZero) (true) else ( animal.weight.toInt() in  weightFrom .. weightTo)

            // String is part of animal name
            val nameCondition = name.isNullOrBlank() || animal.name.contains(
                name,
                ignoreCase = true
            )

            // String is part of animal description
            val descriptionCondition = description.isNullOrBlank() || animal.description.contains(
                description,
                ignoreCase = true
            )

            // check if the city is similar or the same
            val cityCondition = city.isNullOrBlank() || animal.supplier.address?.city?.contains(
                city,
                ignoreCase = true
            ) == true

            // distance is only relevant if the city condition is not already met
            // then calculate if the supplier location is within the selected city's radius
            var distanceCondition = false
            if(!cityCondition){
                if(!city.isNullOrBlank() ) {
                    distanceCondition = this.filterLocation!!.isWithinRangeOf(
                            animal.supplier.address!!.latitude,
                            animal.supplier.address!!.longitude,
                            distanceInKm = distance.toDouble()
                        )
                }
            }

            ageCondition && genderCondition && colorCondition && weightCondition
                    && (cityCondition || distanceCondition)
                    && nameCondition && descriptionCondition && animalCategoryCondition
        }
    }

    /**
     * Update the filtered animal list with the provided filtered animals.
     */
    fun updateFilteredAnimalList(filteredAnimals: List<Animal>) {
        this.filteredAnimals.value = filteredAnimals
    }

    /**
     * Reset all filter values and display all animals.
     */
    fun resetFiltersAndShowAllAnimals() {
        filteredAnimals.value = animalList.value
    }

    /**
     * Reset all filter values to their initial state.
     */
    fun resetFilterValues() {
        initialAgeFrom = 0
        initialAgeTo = 0
        initialSelectedGender = "Alle"
        initialColor = ""
        initialWeightFrom = 0
        initialWeightTo = 0
        initialCity = ""
        initialDistance = 0
        filterLocation = null
        initialName = ""
        initialDescription = ""
        initialBreed = TextFieldValue()
        initialArt = ""
    }

    /**
     * Open the filter dialog.
     */
    fun openFilterDialog() {
        showFilterDialog = true
    }

    /**
     * Get the array of colors for UI selection.
     */
    private fun getLocationByString(locationString: String) {
        runBlocking {
            getLatLongForLocationString(locationString).collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    filterLocation = it.payload as Location
                }
            }
        }
    }

    /**
     * Navigate to the "Add Animal" screen.
     */
    fun navigateToAddAnimal() {
        navigationManager.navigate(Screen.AddAnimal.navigationCommand())
    }

    /**
     * Navigate to the "Login" screen.
     */
    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }

    /**
     * Navigate to the "Animal Detail" screen with the specified animal ID.
     */
    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }

    /**
     * Log out the user.
     */
    fun logout() {
        viewModelScope.launch {
            setLoggedInUserInDataStore(0).collect { result ->
                if (result.status == AsyncOperationState.SUCCESS) {
                    user.value = null
                }
            }
        }
    }
}