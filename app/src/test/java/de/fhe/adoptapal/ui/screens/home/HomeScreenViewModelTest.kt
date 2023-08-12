package de.fhe.adoptapal.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate


@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeScreenViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val getAllAnimals: GetAllAnimals = mockk(relaxed = true)
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase =
        mockk(relaxed = true)
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = HomeScreenViewModel(
            navigationManager,
            getAllAnimals,
            getLoggedInUserFromDataStoreAndDatabase,
            setLoggedInUserInDataStore,
            mockk(),
            mockk(),
            mockk()
        )
    }

    @Test
    fun `getAnimalsFromDb should update animalList`() = runBlockingTest {
        // Arrange
        val animals = listOf(
            Animal(
                name = "Dog",
                birthday = LocalDate.of(2019, 5, 1),
                supplier = User("John Doe", "john@example.com", null),
                animalCategory = AnimalCategory("Dog"),
                description = "Friendly dog",
                color = Color("Brown"),
                imageFilePath = null,
                isMale = true,
                weight = 10.5f
            ),
            Animal(
                name = "Cat",
                birthday = LocalDate.of(2020, 8, 15),
                supplier = User("Jane Smith", "jane@example.com", null),
                animalCategory = AnimalCategory("Cat"),
                description = "Playful cat",
                color = Color("White"),
                imageFilePath = null,
                isMale = false,
                weight = 5.2f
            )
        )
        val successOperation = AsyncOperation.success("Animals retrieved successfully", animals)

        coEvery { getAllAnimals() } returns flowOf(successOperation)

        // Act
        viewModel.getAnimalsFromDb()

        // Assert
        assertEquals(animals, viewModel.animalList.value)
        assertEquals(successOperation, viewModel.dbOp.value)
    }

    @Test
    fun `getLoggedInUser should update user`() = runBlockingTest {
        // Arrange
        val user = User("John Doe", "john@example.com", null)
        val successOperation = AsyncOperation.success("User retrieved successfully", user)

        coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(successOperation)

        // Act
        viewModel.getLoggedInUser()

        // Assert
        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun `logout should clear user`() = runBlockingTest {
        // Arrange
        val user = User("John Doe", "john@example.com", null)
        val successOperation = AsyncOperation.success("User logged out successfully")

        coEvery { setLoggedInUserInDataStore(0) } returns flowOf(successOperation)

        // Set the initial user state
        viewModel.user.value = user

        // Act
        viewModel.logout()

        // Assert
        assertEquals(null, viewModel.user.value)
    }
}

