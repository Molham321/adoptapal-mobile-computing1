package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class DetailScreenViewModel(
    private val navigationManager: NavigationManager,
    private val animalId: Long,
    private val getAnimalAsync: GetAnimalAsync
) : ViewModel() {

    var animal = mutableStateOf<Animal?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        getAnimalFromDb(animalId)
    }

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

    fun getAge(birthday: LocalDate): String {
        val currentDate = LocalDate.now()
        val age = Period.between(birthday, currentDate)

        return if (age.years < 1) {
            if (age.months < 1) {
                "${age.days} Tage"
            } else {
                "${age.months} Monate"
            }
        } else {
            "${age.years} Jahre"
        }
    }

    fun saveAnimalAsFavorite(animal: Animal) {
        println("Tier ${animal.name} mit id ${animal.id} gemerkt: ${animal.isFavorite}")
    }

    fun navigateToUser(userId: Long) {
        navigationManager.navigate(Screen.UserDetail.navigationCommand(userId))
    }
}
