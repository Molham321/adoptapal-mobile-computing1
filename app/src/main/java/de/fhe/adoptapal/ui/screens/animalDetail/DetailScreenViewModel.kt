package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.DeleteAnimalAsync
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.UpdateAnimalAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.GoBackDestination
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import de.fhe.adoptapal.ui.screens.util.FileSystemHandler
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
    private val updateAnimalAsync: UpdateAnimalAsync,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val deleteAnimalAsync: DeleteAnimalAsync

    ) : ViewModel() {

    // Mutable state to hold the animal details
    var animal = mutableStateOf<Animal?>(null)

    private var loggedInUser = mutableStateOf<User?>(null)

    // Mutable state to handle database operation status
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        // Initialize by retrieving animal details from the database
        getAnimalFromDb(animalId)
        getLoggedInUser()
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
     * Marks an animal as favorite and updates the database.
     *
     * @param animal Animal to mark as favorite.
     */
    fun saveAnimalAsFavorite(animal: Animal) {
        viewModelScope.launch {
            updateAnimalAsync(animal).collect {
                // Handle the result of the update if needed
            }
        }
        println("Tier ${animal.name} mit id ${animal.id} gemerkt: ${animal.isFavorite}")
    }

    /**
     * Fetch the logged-in user data from the data store and database.
     */
    private fun getLoggedInUser() {
        viewModelScope.launch {
            getLoggedInUserFromDataStoreAndDatabase().collect {
                if (it.status == AsyncOperationState.SUCCESS) {
                    loggedInUser.value = it.payload as User
                }
            }
        }
    }

    fun deleteAnimal() {

        viewModelScope.launch {
            animal.value?.let { animal ->
                animal.imageFilePath?.let { FileSystemHandler.deleteFile(it) }
                deleteAnimalAsync(animal).collect {
                    if (it.status == AsyncOperationState.SUCCESS) {
                        navigationManager.navigate(GoBackDestination)
                    }
                }
            }
        }
    }

    fun isLoggedInUserAnimalSupplier(): Boolean {
        if(animal.value?.supplier?.id == loggedInUser.value?.id ) {
            return true
        }
        return false
    }


    /**
     * Navigates to the user detail screen.
     *
     * @param userId ID of the user to display details for.
     */
    fun navigateToUser(userId: Long) {
        navigationManager.navigate(Screen.UserDetail.navigationCommand(userId))
    }

    fun navigateToUpdateAnimal(animalId: Long) {
        navigationManager.navigate(Screen.UpdateAnimal.navigationCommand(animalId))
    }
}
