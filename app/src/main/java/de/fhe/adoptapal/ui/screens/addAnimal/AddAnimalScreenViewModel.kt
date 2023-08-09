package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.CreateAnimalAsync
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllColors
import de.fhe.adoptapal.domain.GetAnimalCategoryAsync
import de.fhe.adoptapal.domain.GetColorAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class AddAnimalScreenViewModel(
    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager,
    private val getAllAnimalCategories: GetAllAnimalCategories,
    private val getAllColors: GetAllColors,
    private val getAnimalCategoryAsync: GetAnimalCategoryAsync,
    private val getAnimalColorAsync: GetColorAsync,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
) : ViewModel() {
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalColorList = mutableStateOf(emptyList<Color>())

    var dbOp = mutableStateOf(AsyncOperation.undefined())
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    /**
    * first get all AnimalCategories and Colors from database to display them
    * in a respective dropdown list for the user to select
    */
    init {
        this.getAnimalCategoriesFromDb()
        this.getColorsFromDb()
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
    * fills an array with all AnimalCategories (AnimalCategories are received in form of a list from database)
    */
    fun getCategoryArray(list: List<AnimalCategory>): Array<String> {
        var categoryArray = arrayOf<String>()

        list.forEach {
            categoryArray += it.name
        }

        return categoryArray
    }

    /**
    * fills an array with all Colors (Colors are received in form of a list from database)
    */
    fun getColorArray(list: List<Color>): Array<String> {
        var colorArray = arrayOf<String>()

        list.forEach {
            colorArray += it.name
        }

        return colorArray
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
    * saves a new animal to database
    *
    * animalName: from respective input field
    * birthdate: from respective datepicker (string is parsed into LocalDate)
    * user: currently logged in user is received from database
    * category: id from respective dropdown --> AnimalCategory with id is received from database
    *           (runBlocking to ensure AnimalCategory entry is present at point of animal insert
    *            --> only AnimalCategories from database will be displayed in dropdown so value is never null)
    * animalCategory: from respective input field
    * color: id from respective dropdown --> Color with id is received from database
    *        (runBlocking to ensure Color entry is present at point of animal insert
    *         --> only Colors from database will be displayed in dropdown so value is never null)
    * imageFilePath: null if no image is selected, otherwise filePath of uploaded image
    * animalGender: from respective switch
    * animalWeight: from respective input field
    *
    * after successful animal insert: user is redirected to home
    */
    fun addAnimal(
        animalName: String,
        animalDescription: String,
        animalCategory: Long,
        animalColor: Long,
        animalBirthdate: String,
        animalWeight: Float,
        animalGender: Boolean,
        imageUri: String?
    ) {
        viewModelScope.launch {

            if (animalName.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Animal name, description, category, color, birthdate and weight missing"))
            } else {

                val birthdate =
                    LocalDate.parse(animalBirthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))

                val category = mutableStateOf<AnimalCategory?>(null)
                val color = mutableStateOf<Color?>(null)

                val user = getLoggedInUser()
                println("user: $user")

                runBlocking {
                    getAnimalCategoryAsync(animalCategory).collect {
                        dbOp.value = it
                        if (it.status == AsyncOperationState.SUCCESS) {
                            category.value = it.payload as AnimalCategory
                        }
                    }

                    getAnimalColorAsync(animalColor).collect {
                        dbOp.value = it
                        if (it.status == AsyncOperationState.SUCCESS) {
                            color.value = it.payload as Color
                        }
                    }
                }

                val newAnimal = Animal(
                    animalName,
                    birthdate,
                    user!!,
                    category.value!!,
                    animalDescription,
                    color.value!!,
                    null,
                    animalGender,
                    animalWeight
                )

                newAnimal.imageFilePath = imageUri

                println("$newAnimal")

                createAnimalAsync(newAnimal).collect {
                    saveFeedbackFlow.emit(it)
                    navigateToUserList()
                }
            }
        }
    }

    /**
     * get User from local store
     */
    fun getLoggedInUser(): User? {
        var user: User? = null
        runBlocking {
            getLoggedInUserFromDataStoreAndDatabase().collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    user = it.payload as User
                }
                if (it.status == AsyncOperationState.ERROR) {
                    user = null
                }
            }
        }

        return user
    }

    fun navigateToUserList() {
        navigationManager.navigate(GoBackDestination)
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
}
