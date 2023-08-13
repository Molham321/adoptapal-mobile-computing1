package de.fhe.adoptapal.ui.screens.updateAnimal

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllColors
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.UpdateAnimalAsync
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateAnimalScreenViewModel(
    private val navigationManager: NavigationManager,
    private val animalId: Long,
    private val getAnimalAsync: GetAnimalAsync,
    private val updateAnimalAsync: UpdateAnimalAsync,
    private val getAllAnimalCategories: GetAllAnimalCategories,
    private val getAllColors: GetAllColors,
) : ViewModel() {

    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalColorList = mutableStateOf(emptyList<Color>())

    // Mutable state flows for saving operation feedback and user data
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    // Mutable state to hold the animal details
    var animal = mutableStateOf<Animal?>(null)

    // Mutable state to handle database operation status
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        Log.i("UpdateAnimal", "$animalId")
        // Initialize by retrieving animal details from the database
        this.getAnimalFromDb(animalId)
        this.getAnimalCategoriesFromDb()
        this.getColorsFromDb()
    }

    /**
     * Retrieves animal details from the database.
     *
     * @param id ID of the animal to retrieve.
     */
    private fun getAnimalFromDb(id: Long) {
        viewModelScope.launch {
            getAnimalAsync.invoke(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animal.value = it.payload as Animal
                }
            }
        }
    }

    /**
     * get all AnimalCategories from database
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
     * get all Colors from database
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
     * fills a map with all AnimalCategories (value) and their id (key)
     * --> can be displayed easier in a dropdown this way; id is
     * needed to save a new animal to the database
     */
    fun getCategoryMap(list: List<AnimalCategory>): Map<Long, String> {
        val categoryMap = mutableMapOf<Long, String>()

        list.forEach {
            categoryMap[it.id] = it.name
        }

        return categoryMap
    }

    /**
     * fills a map with all Colors (value) and their id (key)
     * --> can be displayed easier in a dropdown this way; id is
     * needed to save a new animal to the database
     */
    fun getColorMap(list: List<Color>): Map<Long, String> {
        val colorMap = mutableMapOf<Long, String>()

        list.forEach {
            colorMap[it.id] = it.name
        }

        return colorMap
    }

    /**
     * validate that birthdate is not greater than current date
     */
    fun validateBirthdate(animalBirthdateValue: String): Boolean {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        try {
            val birthdate = dateFormat.parse(animalBirthdateValue)
            val currentDate = Date()

            if (birthdate.after(currentDate)) {
                return false
            }

        } catch (e: Exception) {
            return false
        }

        return true
    }

    /**
     * validate that weight consists of numbers separated by a dot
     */
    fun validateWeight(text: String): Boolean {
        val regex = Regex("""^\d+(\.\d+)?$""")
        return regex.matches(text)
    }

    fun updateAnimal(updateAnimal: Animal) {
        viewModelScope.launch {
            updateAnimalAsync(updateAnimal).collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    saveFeedbackFlow.emit(it)
                    navigationManager.navigate(GoBackDestination)
                } else {
                    Log.i("Error!", "Das hat nicht funktioniert!")
                }

                if (it.status == AsyncOperationState.ERROR) {
                    saveFeedbackFlow.emit(it)
                }
            }
        }
    }

    fun navigateToUserList() {
        navigationManager.navigate(GoBackDestination)
    }
}