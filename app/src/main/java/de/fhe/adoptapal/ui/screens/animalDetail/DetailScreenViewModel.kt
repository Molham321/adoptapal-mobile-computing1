package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.runtime.mutableStateOf
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
    var animal: Animal? = null
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
     animal  = this.getAnimalFromDb(animalId)
}

    private fun getAnimalFromDb(id: Long) : Animal?{

        viewModelScope.launch {
            getAnimalAsync.invoke(id).map {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    return@map it.payload
                } else {
                    return@map null
                }
            }
        }
        return null
    }
}