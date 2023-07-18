package de.fhe.adoptapal

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.fhe.adoptapal.android_core.LocalStoreImpl
import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.RepositoryImpl
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.CreateAnimalAsync
import de.fhe.adoptapal.domain.CreateAnimalCategoryAsync
import de.fhe.adoptapal.domain.CreateColorAsync
import de.fhe.adoptapal.domain.DeleteAnimalAsync
import de.fhe.adoptapal.domain.GetAddressAsync
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetAllColors
import de.fhe.adoptapal.domain.GetAllFavoriteAnimalsAsync
import de.fhe.adoptapal.domain.GetAllRatingsBySeekerId
import de.fhe.adoptapal.domain.GetAllRatingsBySupplierId
import de.fhe.adoptapal.domain.GetAllUsers
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.GetAnimalByRangeAsync
import de.fhe.adoptapal.domain.GetAnimalCategoryAsync
import de.fhe.adoptapal.domain.GetColorAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.GetRatingAsync
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.domain.GetUsersByRangeAsync
import de.fhe.adoptapal.domain.InsertAddressAsync
import de.fhe.adoptapal.domain.InsertRatingAsync
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.LocalStore
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.network.retrofit.RetrofitNetworkController
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UseCaseTests : KoinTest {

    val koinTestModule = module {

        single<Repository> {
            RepositoryImpl(
                AppDatabase.getUserModelDao(get()),
                AppDatabase.getAddressModelDao(get()),
                AppDatabase.getRatingModelDao(get()),
                AppDatabase.getAnimalModelDao(get()),
                AppDatabase.getAnimalCategoryModelDao(get()),
                AppDatabase.getColorModelDao(get()),
                AppDatabase.getRequestModelDao(get())
            )
        }

        single {
            RetrofitNetworkController()
        }


        single {
            NavigationManager()
        }

        single<LocalStore> {
            LocalStoreImpl(get())
        }

        // animal
        factory { GetAllAnimals(get()) }
        factory { GetAnimalByRangeAsync(get()) }
        factory { CreateAnimalAsync(get()) }
        factory { GetAnimalAsync(get()) }
        factory { GetUserAnimalsAsync(get()) }
        factory { GetAllFavoriteAnimalsAsync(get()) }
        factory { DeleteAnimalAsync(get()) }

        // color
        factory { GetAllColors(get()) }
        factory { CreateColorAsync(get()) }
        factory { GetColorAsync(get()) }

        // animal category
        factory { GetAllAnimalCategories(get()) }
        factory { CreateAnimalCategoryAsync(get()) }
        factory { GetAnimalCategoryAsync(get()) }

        // user
        factory { GetAllUsers(get()) }
        factory { GetUsersByRangeAsync(get()) }
        factory { GetUserAsync(get()) }
        factory { GetUserByEmailAsync(get()) }
        factory { InsertUserAsync(get()) }
        factory { UpdateUserAsync(get()) }

        // Address
        factory { GetAddressAsync(get()) }
        factory { InsertAddressAsync(get()) }

        // Rating
        factory { GetRatingAsync(get()) }
        factory { InsertRatingAsync(get()) }
        factory { GetAllRatingsBySeekerId(get()) }
        factory { GetAllRatingsBySupplierId(get()) }

        // LocalStore
        factory { GetLoggedInUserFromDataStoreAndDatabase(get(), get()) }
        factory { SetLoggedInUserInDataStore(get()) }
    }

    @Before
    fun setup() {
        loadKoinModules(koinTestModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(koinTestModule)
    }

    @Test
    fun testGetUserByEmailAsyncUseCase() = runBlocking {

        val repo = get<Repository>()

        val userEmail = "user@mailhot.net"
        repo.insertUser(User("Name", userEmail, null, null))

        val getUserByEmailAsync = get<GetUserByEmailAsync>()

        getUserByEmailAsync.invoke(userEmail).collect {
            if (it.status == AsyncOperationState.SUCCESS) {
                assertTrue(
                    "User with provided email should exist",
                    (it.payload as User).email == userEmail
                )
            }
        }
    }

    @Test
    fun testUpdateUserAsyncUseCase() = runBlocking {

        val repo = get<Repository>()

        repo.deleteAllUsers()

        val userEmail = "user@mailhot.net"

        var user = User("Name", userEmail, null, null)

        val userId = repo.insertUser(user)
        user.id = userId

        // test if user is there
        val getUserByEmailAsync = get<GetUserByEmailAsync>()
        getUserByEmailAsync.invoke(userEmail).collect {
            if (it.status == AsyncOperationState.SUCCESS) {
                assertTrue(
                    "User with provided email should exist",
                    (it.payload as User).email == userEmail
                )
                assertTrue(
                    "User id is equal to existing userId: $userId and payloadUserId: ${(it.payload as User).id}",
                    (it.payload as User).id == userId
                )
                user = it.payload as User
            }
        }

        // test update
        val updateUserAsync = get<UpdateUserAsync>()
        val newUserEmail = "new@user.mail"

        user.email = newUserEmail
        updateUserAsync.invoke(user).collect {
            if (it.status == AsyncOperationState.SUCCESS) {
                assertTrue("User should have same id", (it.payload as Long) == user.id)
            }
        }

        // test getUser again with new email
        getUserByEmailAsync.invoke(newUserEmail).collect {
            if (it.status == AsyncOperationState.SUCCESS) {
                assertTrue(
                    "User with new email should exist",
                    (it.payload as User).email == newUserEmail
                )
                assertTrue("Id of user should be the same", (it.payload as User).id == userId)
            }
        }


    }

    @Test
    fun testGetAndSetLoggerInUser() = runBlocking {

        // prepare
        val user = User("TestName", "Test@Mail.de", "1234", null)
        val userId = get<Repository>().insertUser(user)

        // test
        val setLoggedInUserInDataStore = get<SetLoggedInUserInDataStore>()
        //setLoggedInUserInDataStore(userId)

        // check
        val getLoggedInUserFromDataStoreAndDatabase = get<GetLoggedInUserFromDataStoreAndDatabase>()
        getLoggedInUserFromDataStoreAndDatabase().collect {
            if (it == AsyncOperation.success()) {
                assertEquals(userId, (it.payload as User).id)
                assertEquals(user.name, (it.payload as User).name)
                assertEquals(user.email, (it.payload as User).email)
            }
        }

    }


}