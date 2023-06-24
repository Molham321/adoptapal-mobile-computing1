package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.CreateAnimalAsync
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddAnimalViewModel(
//    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager,
    private val getAllAnimalCategories: GetAllAnimalCategories
) : ViewModel() {
    var animalCategoryList = mutableStateOf(emptyList<AnimalCategory>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    init {
        this.getAnimalCategoriesFromDb()
    }

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

//    fun addAnimal(animalName: String) {
//        viewModelScope.launch {
//
//            if (animalName.isBlank()) {
//                saveFeedbackFlow.emit(AsyncOperation.error("Animal name missing"))
//            } else {
////                val newUser = User(userName)
////
////                addUserAsyncUseCase(newUser).collect {
////                    saveFeedbackFlow.emit(it)
////
////
////                }
////                println("$animalName")
//            }
//        }
//    }

//    fun navigateToUserList() {
//        navigationManager.navigate(GoBackDestination)
//    }
}
