package de.fhe.adoptapal.ui.screens.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.android_core.LoggerFactory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllUsers
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch

/**
 * ViewModel for Map screen
 */
class MapScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getAllUsers: GetAllUsers
) : ViewModel() {


    private val logger = LoggerFactory.getLogger()

    val userList = mutableStateOf(emptyList<User>())
    var dbOp = mutableStateOf(AsyncOperation.undefined())

    init {
        logger.info(LOGTAG, "MapsScreenViewModel created")
        this.getUsersFromDB()
    }

    /**
     * load users from database
     */
    private fun getUsersFromDB() {
        viewModelScope.launch {
            getAllUsers().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    userList.value = it.payload as List<User>
                }
            }
        }
    }

    fun navigateToUser(userId: Long) {
        navigationManager.navigate((Screen.UserDetail.navigationCommand(userId)))
    }

}