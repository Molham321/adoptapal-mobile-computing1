package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.core.LoggerFactory
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetAllFavoriteAnimalsAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileScreenViewModel(
    private val navigationManager: NavigationManager,
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase,
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore,
    private val getAllFavoriteAnimalsAsync: GetAllFavoriteAnimalsAsync,
    private val getUserAnimalsAsync: GetUserAnimalsAsync,
    private val loggerFactory: LoggerFactory
) : ViewModel() {

    val logger = loggerFactory.getLogger()

    private val LOGTAG = "ProfileVM"

    var dbOp = mutableStateOf(AsyncOperation.undefined())
    var user = mutableStateOf<User?>(null)
    var favoriteAnimalList = mutableStateOf(emptyList<Animal>())
    var userAnimalList = mutableStateOf(emptyList<Animal>())


    /**
    * 1. get current user from database (if user is logged in)
    * 2. get all favourite animals of user (later saved locally on user's phone so no login is needed)
    * 3. if there is a logged in user: get all uploaded animals of said user with the respective user id
    *
    */
    init {
        logger.info(LOGTAG, "init class")
        this.getUser()
        this.getFavoriteAnimalsFromDb()
        if(user.value != null) {
            this.getUserAnimalsFromDb(user.value!!.id)
        }
    }

    /**
    * function is runBlocking, to guarantee a user when profile page is opened (and user is logged in)
    * otherwise (when no user is logged in) function will end and page will be opened without user data
    * --> favourite animals will still be displayed
    */
    fun getUser() {
        logger.info(LOGTAG, "init")
        runBlocking {
            logger.info(LOGTAG, "launching")
            getLoggedInUserFromDataStoreAndDatabase().collect {
                logger.info(LOGTAG, "Collecting")
                if (it.status == AsyncOperationState.SUCCESS) {
                    logger.info(LOGTAG, "found user with id: ${(it.payload as User).id}")
                    user.value = it.payload as User
                }
                if (it.status == AsyncOperationState.ERROR) {
                    logger.info(LOGTAG, "Failed to load user")
                }
            }
        }
    }

    fun reload() {
        viewModelScope.launch {
            getUser()
        }
    }

    /**
    * gets all favourite animals from database (saved on user's phone locally)
    */
    private fun getFavoriteAnimalsFromDb() {
        viewModelScope.launch {
            getAllFavoriteAnimalsAsync().collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    favoriteAnimalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    /**
    * gets all animals uploaded by the currently logged in user (with id of that user)
    */
    private fun getUserAnimalsFromDb(id: Long) {
        viewModelScope.launch {
            getUserAnimalsAsync(id).collect {
                dbOp.value = it
                if (it.status == AsyncOperationState.SUCCESS) {
                    userAnimalList.value = it.payload as List<Animal>
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            setLoggedInUserInDataStore(0).collect { result ->
                if (result.status == AsyncOperationState.SUCCESS) {
                    user.value = null
                    navigateToHome()
                }
            }
        }
    }

    fun navigateToSettings() {
        navigationManager.navigate(Screen.Settings.navigationCommand())
    }

    fun navigateToAnimal(animalId: Long) {
        navigationManager.navigate(Screen.Detail.navigationCommand(animalId))
    }

    fun navigateToLogin() {
        navigationManager.navigate(Screen.Login.navigationCommand())
    }

    private fun navigateToHome() {
        navigationManager.navigate(Screen.Home.navigationCommand())
    }

}