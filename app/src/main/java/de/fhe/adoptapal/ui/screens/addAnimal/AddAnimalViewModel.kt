package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.runtime.MutableState
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
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddAnimalViewModel(
    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager,
    private val getAllAnimalCategories: GetAllAnimalCategories,
    private val getAllColors: GetAllColors
) : ViewModel() {
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var animalColorList = mutableStateOf(emptyList<Color>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

//    var animalCategoryArray = arrayOf<String>()
//    var animalCategories = mutableStateOf(emptyArray<String>())

    init {
        this.getAnimalCategoriesFromDb()
        this.getColorsFromDb()
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

        return  colorArray
    }


    fun addAnimal(
        animalName: String,
        animalDescription: String,
        animalCategory: String,
        animalColor: String,
        animalBirthdate: String,
        animalWeight: Float,
        animalGender: Boolean
    ) {
        viewModelScope.launch {

            if (animalName.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Animal name missing"))
            } else {
                println(animalName)
                println(animalDescription)
                println(animalCategory)
                println(animalColor)
                println(animalBirthdate)
                println(animalWeight)
                println(animalGender)

                val birthdate = LocalDateTime.parse(animalBirthdate, DateTimeFormatter.BASIC_ISO_DATE)

//                val newAnimal = Animal(
//                    animalName,
//                    birthdate
//
//                )

//                val newUser = User(userName)
//
//                addUserAsyncUseCase(newUser).collect {
//                    saveFeedbackFlow.emit(it)
//
//
//                }
//                println("$animalName")
            }
        }
    }

//    fun navigateToUserList() {
//        navigationManager.navigate(GoBackDestination)
//    }
}
