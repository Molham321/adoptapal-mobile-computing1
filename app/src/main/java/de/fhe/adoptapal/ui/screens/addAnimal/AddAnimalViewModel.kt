package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.CreateAnimalAsync
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddAnimalViewModel(
    private val createAnimalAsync: CreateAnimalAsync,
    private val navigationManager: NavigationManager
) : ViewModel() {

    val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())

    fun addAnimal(animalName: String) {
        viewModelScope.launch {

            if (animalName.isBlank()) {
                saveFeedbackFlow.emit(AsyncOperation.error("Animal name missing"))
            } else {
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

    fun navigateToUserList() {
        navigationManager.navigate(GoBackDestination)
    }
}
