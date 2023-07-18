package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.launch

class UserDetailScreenViewModel(
    private val userId: Long,
    private val getUserAsync: GetUserAsync
) : ViewModel() {
    var user = mutableStateOf<User?>(null)
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        getUserFromDb(userId)
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
}

