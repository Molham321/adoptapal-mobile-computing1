package de.fhe.adoptapal.ui.screens.addAnimal

import android.widget.Toast
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
import de.fhe.adoptapal.domain.GetAnimalCategoryAsync
import de.fhe.adoptapal.domain.GetColorAsync
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddAnimalViewModel(
    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager,
    private val getAllAnimalCategories: GetAllAnimalCategories,
    private val getAllColors: GetAllColors,
    private val getUserAsync: GetUserAsync,
    private val getAnimalCategoryAsync: GetAnimalCategoryAsync,
    private val getAnimalColorAsync: GetColorAsync
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
        this.getSupplyingUser(2)
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
                saveFeedbackFlow.emit(AsyncOperation.error("Animal name, description, category, color, birthdate and weight missing"))
            } else {
                println(animalName)
                println(animalDescription)
                println(animalCategory)
                println(animalColor)
                println(animalBirthdate)
                println(animalWeight)
                println(animalGender)

                val birthdate = LocalDate.parse(animalBirthdate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))


                val user = supplyingUser.value!!

                println("user: " + user)


                // println("selected date: " + birthdate)

//                val newAnimal = Animal(
//                    animalName,
//                    birthdate,
//                    user,
//                    animalCategory,
//                    animalDescription,
//                    animalColor,
//                    null,
//                    animalGender,
//                    animalWeight
//                )
//
//                println("$animalName")
            }
        }
    }

    fun navigateToUserList() {
        navigationManager.navigate(GoBackDestination)
    }
}
