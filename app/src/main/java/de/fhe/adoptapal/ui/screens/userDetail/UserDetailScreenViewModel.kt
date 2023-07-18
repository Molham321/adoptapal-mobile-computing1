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

class UserDetailScreenViewModel(
    private val navigationManager: NavigationManager,
    private val userId: Long,
    private val getUserAsync: GetUserAsync,
    private  val getUserAnimalsAsync: GetUserAnimalsAsync
) : ViewModel() {
    var user = mutableStateOf<User?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var animalList = mutableStateOf(emptyList<Animal>())


    init {
        getUserFromDb(userId)
        getUserAnimalsFromDb(userId)
    }

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

    fun getUserAnimalsFromDb(id: Long) {
        viewModelScope.launch {
            getUserAnimalsAsync(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }
}

