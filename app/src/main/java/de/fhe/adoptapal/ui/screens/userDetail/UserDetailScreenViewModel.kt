package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch

/**
 * ViewModel class for the User Detail screen. Manages user details and associated animal information.
 *
 * @param navigationManager NavigationManager instance for handling navigation.
 * @param userId The ID of the user whose details are being displayed.
 * @param getUserAsync Use case to fetch user details asynchronously.
 * @param getUserAnimalsAsync Use case to fetch animals associated with the user asynchronously.
 */
class UserDetailScreenViewModel(
    private val navigationManager: NavigationManager,
    private val userId: Long,
    private val getUserAsync: GetUserAsync,
    private val getUserAnimalsAsync: GetUserAnimalsAsync
) : ViewModel() {
    // Mutable state values to hold user details, database operation status, and animal list.
    var user = mutableStateOf<User?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var animalList = mutableStateOf(emptyList<Animal>())

    // Initialize by fetching user and associated animal data from the database.
    init {
        getUserFromDb(userId)
        getUserAnimalsFromDb(userId)
    }

    /**
     * Fetches user details from the database using the given user ID.
     *
     * @param id The ID of the user to fetch.
     */
    fun getUserFromDb(id: Long) {
        viewModelScope.launch {
            getUserAsync.invoke(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    user.value = it.payload as User
                }
            }
        }
    }

    /**
     * Fetches a list of animals associated with the user from the database.
     *
     * @param id The ID of the user.
     */
    private fun getUserAnimalsFromDb(id: Long) {
        viewModelScope.launch {
            getUserAnimalsAsync(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    /**
     * Navigates to the animal detail screen when an animal is clicked.
     *
     * @param animalId The ID of the clicked animal.
     */
    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }
}

