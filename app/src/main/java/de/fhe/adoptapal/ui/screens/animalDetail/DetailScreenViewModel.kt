package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.UpdateAnimalAsync
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val navigationManager: NavigationManager,
    private val animalId: Long,
    private val getAnimalAsync: GetAnimalAsync,
    private val updateAnimalAsync: UpdateAnimalAsync
) : ViewModel() {

    var animal = mutableStateOf<Animal?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        getAnimalFromDb(animalId)
    }

    /**
     * get animal from database (id comes from selected list entry on home screen)
     */
    fun getAnimalFromDb(id: Long) {
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
     * marks animal as favourite / unmarks animal as favourite when icon button is clicked
     * --> function displays message on screen
     */
    fun saveAnimalAsFavorite(animal: Animal) {
        viewModelScope.launch {
            updateAnimalAsync(animal).collect {

            }
        }
        println("Tier ${animal.name} mit id ${animal.id} gemerkt: ${animal.isFavorite}")
    }

    fun navigateToUser(userId: Long) {
        navigationManager.navigate(Screen.UserDetail.navigationCommand(userId))
    }
}
