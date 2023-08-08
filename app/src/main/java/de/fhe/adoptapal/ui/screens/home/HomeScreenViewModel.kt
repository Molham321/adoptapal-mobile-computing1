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
import de.fhe.adoptapal.domain.GetLatLongForAddress
import de.fhe.adoptapal.domain.GetLatLongForLocationString
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinApplication.Companion.init
import java.time.LocalDate

class HomeScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllAnimals: GetAllAnimals,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    private val getAllColors: GetAllColors,
    private val getLatLongForLocationString: GetLatLongForLocationString,
    private val getAllAnimalCategories: GetAllAnimalCategories,
) : ViewModel() {

    var showFilterDialog      by mutableStateOf(false)
    var initialAgeFrom        by mutableStateOf(0)
    var initialAgeTo          by mutableStateOf(0)
    var initialWeightFrom     by mutableStateOf(0)
    var initialWeightTo       by mutableStateOf(0)
    var initialCity           by mutableStateOf("")
    var initialName           by mutableStateOf("")
    var initialDescription    by mutableStateOf("")
    var initialColor          by mutableStateOf("")
    var initialArt            by mutableStateOf("")
    var initialSelectedGender by mutableStateOf("Alle")
    var initialBreed          by mutableStateOf(TextFieldValue())
    var initialDistance       by mutableStateOf(0)

    var animalColorList    = mutableStateOf(emptyList<Color>())
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalList         = mutableStateOf(emptyList<Animal>())
    var filteredAnimals    = mutableStateOf(emptyList<Animal>())
    var dbOp               = mutableStateOf(AsyncOperation.undefined())
    var user               = mutableStateOf<User?>(null)
    var filterLocation by mutableStateOf<Location?>(null)

    init {
        this.getLoggedInUser()
        this.getAnimalsFromDb()
        this.getColorsFromDb()
        this.getAnimalCategoriesFromDb()
    }

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
        if(city != null) {
            this.getLocationByString(city)
        }
        return animalList.value.filter { animal ->
            val ageCondition =
                (ageFrom == null || animal.birthday.plusYears(ageFrom.toLong()) >= LocalDate.now()) &&
                        (ageTo == null || animal.birthday.plusYears(ageTo.toLong()) <= LocalDate.now())
            val maleCondition = isMale == null || animal.isMale == isMale
            val colorCondition = color.isNullOrBlank() || animal.color.name.contains(color, ignoreCase = true)
            val artCondition   = art.isNullOrBlank()   || animal.animalCategory.name.contains(art, ignoreCase = true)
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
            if(!city.isNullOrBlank() && distance > 0) {
                distanceCondition = this.filterLocation!!.isWithinRangeOf(
                        animal.supplier.address!!.latitude,
                        animal.supplier.address!!.longitude,
                        distanceInKm = distance.toDouble()
                    )
            }
            ageCondition && maleCondition && colorCondition && weightCondition && (cityCondition || distanceCondition) && nameCondition && descriptionCondition && artCondition
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
        initialDistance = 0
        filterLocation = null
        initialName = ""
        initialDescription = ""
        initialBreed = TextFieldValue()
        initialArt = ""
    }

    fun openFilterDialog() {
        showFilterDialog = true
    }

    private fun getLocationByString(locationString: String) {
         runBlocking {
            getLatLongForLocationString(locationString).collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    filterLocation = it.payload as Location
                }
            }
        }
    }

    // -----------------
    // Navigation
    // -----------------

    fun getColorArray(list: List<Color>): Array<String> {
        var colorArray = arrayOf<String>()

        list.forEach {
            colorArray += it.name
        }

        return colorArray
    }

    fun getColorMap(list: List<Color>): Map<Long, String> {
        var colorMap = mutableMapOf<Long, String>()

        list.forEach {
            colorMap[it.id] = it.name
        }

        return colorMap
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