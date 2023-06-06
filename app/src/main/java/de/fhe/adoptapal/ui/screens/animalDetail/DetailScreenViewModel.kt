package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAnimalAsync
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val animalId: Long,
    private val getAnimalAsync: GetAnimalAsync
) : ViewModel() {

    var animal = mutableStateOf<Animal?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        getAnimalFromDb(animalId)
    }


    private fun getAnimalFromDb(id: Long) {
        viewModelScope.launch {
            getAnimalAsync.invoke(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    animal.value = it.payload as Animal
                }
            }
        }
    }
}
