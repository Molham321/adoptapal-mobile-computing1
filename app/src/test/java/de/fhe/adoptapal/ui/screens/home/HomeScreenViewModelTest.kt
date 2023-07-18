package de.fhe.adoptapal.ui.screens.home

import de.fhe.adoptapal.domain.*
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import io.mockk.coEvery
import io.mockk.mockk
import java.time.LocalDate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule


@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeScreenViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val getAllAnimals: GetAllAnimals = mockk(relaxed = true)
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase = mockk(relaxed = true)
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = HomeScreenViewModel(
            navigationManager,
            getAllAnimals,
            getLoggedInUserFromDataStoreAndDatabase,
            setLoggedInUserInDataStore
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
    fun `getAge should calculate the correct age string`() {
        // Arrange
        val birthday = LocalDate.of(1990, 5, 1)
        val expectedAgeString = "33 years"

        // Act
        val ageString = viewModel.getAge(birthday)

        // Assert
        assertEquals(expectedAgeString, ageString)
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

