package de.fhe.adoptapal

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.RepositoryImpl
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
import de.fhe.adoptapal.domain.GetRatingAsync
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.domain.GetUsersByRangeAsync
import de.fhe.adoptapal.domain.InsertAddressAsync
import de.fhe.adoptapal.domain.InsertRatingAsync
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.flow.first
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
class UseCaseTests: KoinTest {

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
            NavigationManager()
        }


        // animal
        factory { GetAllAnimals(get()) }
        factory { GetAnimalByRangeAsync(get()) }
        factory { CreateAnimalAsync(get()) }
        factory { GetAnimalAsync(get()) }
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

        // Address
        factory { GetAddressAsync(get()) }
        factory { InsertAddressAsync(get()) }

        // Rating
        factory { GetRatingAsync(get()) }
        factory { InsertRatingAsync(get()) }
        factory { GetAllRatingsBySeekerId(get()) }
        factory { GetAllRatingsBySupplierId(get()) }
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
            if(it.status == AsyncOperationState.SUCCESS) {
                assertTrue("User with provided email should exist", (it.payload as User).email == userEmail)
            }
        }
    }
}