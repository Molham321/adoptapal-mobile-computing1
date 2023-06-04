package de.fhe.adoptapal.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.fhe.adoptapal.data.FakeDatabase
import de.fhe.adoptapal.model.Animal
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen

class HomeScreenViewModel(
    private val navigationManager: NavigationManager
) : ViewModel() {
    var animalList by mutableStateOf(emptyList<Animal>())

    init {
        this.getAnimalsFromDb()
    }

    private fun getAnimalsFromDb() {
        animalList = FakeDatabase.AnimalList
    }

    fun navigateToAddAnimal() {
        navigationManager.navigate(Screen.Input.navigationCommand())
    }

    fun navigateToAnimal(animalId: Int) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }
}