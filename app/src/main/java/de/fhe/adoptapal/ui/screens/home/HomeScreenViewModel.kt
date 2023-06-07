package de.fhe.adoptapal.ui.screens.home

//import de.fhe.adoptapal.model.Animal
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllAnimals: GetAllAnimals
) : ViewModel() {
    var animalList = mutableStateOf(emptyList<Animal>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())


    init {
        this.getAnimalsFromDb()
    }

    private fun getAnimalsFromDb() {
        viewModelScope.launch {
            getAllAnimals().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animalList.value = it.payload as List<Animal>
                }
            }
        }
//        animalList = FakeDatabase.AnimalList
    }

    fun navigateToAddAnimal() {
        navigationManager.navigate(Screen.Input.navigationCommand())
    }

    fun navigateToSearch() {
        navigationManager.navigate(Screen.Search.navigationCommand())
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }
}