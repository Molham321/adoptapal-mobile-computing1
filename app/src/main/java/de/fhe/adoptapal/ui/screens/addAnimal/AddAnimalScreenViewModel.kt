package de.fhe.adoptapal.ui.screens.addAnimal

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.map.LOGTAG
import de.fhe.adoptapal.ui.screens.util.FileSystemHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

// TODO: aktuell angemeldeten Nutzer mit dem neuen Tier speichern
// TODO: eigene Bilder hochladen

class AddAnimalScreenViewModel(
    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager,
    private val getAllAnimalCategories: GetAllAnimalCategories,
    private val getAllColors: GetAllColors,
    private val getUserAsync: GetUserAsync,
    private val getAnimalCategoryAsync: GetAnimalCategoryAsync,
    private val getAnimalColorAsync: GetColorAsync,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
) : ViewModel() {
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalColorList = mutableStateOf(emptyList<Color>())
    var supplyingUser = mutableStateOf<User?>(null)


    var dbOp = mutableStateOf(AsyncOperation.undefined())
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

//    var animalCategoryArray = arrayOf<String>()
//    var animalCategories = mutableStateOf(emptyArray<String>())

    init {
        this.getAnimalCategoriesFromDb()
        this.getColorsFromDb()
        this.getSupplyingUser(2) // TODO muss die ID hardcoded sein?
//        animalCategoryList.value.forEach {
//            animalCategoryArray += it.name
//        }
//        animalCategories = mutableStateOf(animalCategoryArray)
    }

    private fun getAnimalCategoriesFromDb() {
        viewModelScope.launch {
            getAllAnimalCategories().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalCategoryList.value = it.payload as List<AnimalCategory>
                    // animalCategoryArray = it.payload as Array<String>
                    // println(animalCategoryArray.joinToString(" "))
                }
            }
        }
    }

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

    private fun getSupplyingUser(id: Long) {
        viewModelScope.launch {
            getUserAsync(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    supplyingUser.value = it.payload as User
                }
            }
        }
    }

    fun getCategoryArray(list: List<AnimalCategory>): Array<String> {
        var categoryArray = arrayOf<String>()

        list.forEach {
            categoryArray += it.name
        }

        return categoryArray
    }

    fun getColorArray(list: List<Color>): Array<String> {
        var colorArray = arrayOf<String>()

        list.forEach {
            colorArray += it.name
        }

        return colorArray
    }

    fun getCategoryMap(list: List<AnimalCategory>): Map<Long, String> {
        var categoryMap = mutableMapOf<Long, String>()

        list.forEach {
            categoryMap[it.id] = it.name
        }

        return categoryMap
    }

    fun getColorMap(list: List<Color>): Map<Long, String> {
        var colorMap = mutableMapOf<Long, String>()

        list.forEach {
            colorMap[it.id] = it.name
        }

        return colorMap
    }


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
                println(animalName)
                println(animalDescription)
                println(animalCategory)
                println(animalColor)
                println(animalBirthdate)
                println(animalWeight)
                println(animalGender)

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

                // println("selected date: " + birthdate)

                var newAnimal = Animal(
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

    fun validateWeight(text: String): Boolean {
        val regex = Regex("""^\d+(\.\d+)?$""")
        return regex.matches(text)
    }
}
