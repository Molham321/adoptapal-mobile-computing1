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

/**
 * ViewModel for the animal detail screen.
 *
 * @param navigationManager Navigation manager to handle screen navigation.
 * @param animalId ID of the animal to display details for.
 * @param getAnimalAsync Use case for retrieving an animal.
 * @param updateAnimalAsync Use case for updating an animal.
 */
class DetailScreenViewModel(
    private val navigationManager: NavigationManager,
    private val animalId: Long,
    private val getAnimalAsync: GetAnimalAsync,
    private val updateAnimalAsync: UpdateAnimalAsync
) : ViewModel() {

    // Mutable state to hold the animal details
    var animal = mutableStateOf<Animal?>(null)

    // Mutable state to handle database operation status
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        // Initialize by retrieving animal details from the database
        getAnimalFromDb(animalId)
    }

    /**
     * Retrieves animal details from the database.
     *
     * @param id ID of the animal to retrieve.
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
     * Navigates to the user detail screen.
     *
     * @param userId ID of the user to display details for.
     */
    fun navigateToUser(userId: Long) {
        navigationManager.navigate(Screen.UserDetail.navigationCommand(userId))
    }
}
