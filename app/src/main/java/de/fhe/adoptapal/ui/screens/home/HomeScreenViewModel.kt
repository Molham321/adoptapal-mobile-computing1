package de.fhe.adoptapal.ui.screens.home

import android.util.Log
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
    var initialBreed by mutableStateOf(TextFieldValue())
    var initialDistance by mutableStateOf(0)

    var animalColorList = mutableStateOf(emptyList<Color>())
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalList = mutableStateOf(emptyList<Animal>())
    var filteredAnimals = mutableStateOf(emptyList<Animal>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)
    var filterLocation by mutableStateOf<Location?>(null)

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
    fun getColorsFromDb() {
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
    fun getAnimalCategoriesFromDb() {
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
        ageFrom: Int?,
        ageTo: Int?,
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
            val ageCondition =
                (ageFrom == null || animal.birthday.plusYears(ageFrom.toLong()) >= LocalDate.now()) &&
                        (ageTo == null || animal.birthday.plusYears(ageTo.toLong()) <= LocalDate.now())
            val maleCondition = isMale == null || animal.isMale == isMale
            val colorCondition =
                color.isNullOrBlank() || animal.color.name.contains(color, ignoreCase = true)
            val artCondition =
                art.isNullOrBlank() || animal.animalCategory.name.contains(art, ignoreCase = true)
            val weightCondition = (weightFrom == 0 || animal.weight >= weightFrom) &&
                    (weightTo == 0 || animal.weight <= weightTo)
            val cityCondition = city.isNullOrBlank() || animal.supplier.address?.city?.contains(
                city,
                ignoreCase = true
            ) == true
            val nameCondition = name.isNullOrBlank() || animal.name.contains(
                name,
                ignoreCase = true
            )
            val descriptionCondition = description.isNullOrBlank() || animal.description.contains(
                description,
                ignoreCase = true
            )

            // check if same city
            // check if different City + distance is in range in km from selected city
            // city need to be requested from API to get Location data LatLonng
            // bei = 0 alle Orte
            // bei = 5 , die Stadt und alles im Radius von 5 km
            Log.i("HSVM", "location $distance")
            // load location once
            var distanceCondition = true
            if (!city.isNullOrBlank() && distance > 0) {
                distanceCondition = this.filterLocation!!.isWithinRangeOf(
                    animal.supplier.address!!.latitude,
                    animal.supplier.address!!.longitude,
                    distanceInKm = distance.toDouble()
                )
            }
            ageCondition && maleCondition && colorCondition && weightCondition && (cityCondition || distanceCondition) && nameCondition && descriptionCondition && artCondition
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
        // Hier werden die Filterwerte zurückgesetzt
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