package de.fhe.adoptapal.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsScreenVieModel(
    private val updateUserAsyncUserCase: UpdateUserAsync,
    private val navigationManager: NavigationManager,
): ViewModel() {

    private val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())
    fun updateUser(user: User) {
        viewModelScope.launch {
            updateUserAsyncUserCase(user).collect {
                saveFeedbackFlow.emit(it)
            }
        }
    }
}