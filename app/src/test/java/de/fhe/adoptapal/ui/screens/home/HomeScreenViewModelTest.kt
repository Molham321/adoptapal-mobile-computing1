package de.fhe.adoptapal.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.fhe.adoptapal.domain.*
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime


@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
//    private lateinit var viewModel: HomeScreenViewModel
//    private lateinit var navigationManager: NavigationManager
//    private lateinit var getAllAnimals: GetAllAnimals
//    private lateinit var getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase
//
//    @Before
//    fun setup() {
//        navigationManager = mockk(relaxed = true)
//        getAllAnimals = mockk(relaxed = true)
//        getLoggedInUserFromDataStoreAndDatabase = mockk(relaxed = true)
//
//        viewModel = HomeScreenViewModel(
//            navigationManager,
//            getAllAnimals,
//            getLoggedInUserFromDataStoreAndDatabase
//        )
//    }
//
//    @Test
//    fun `getAnimalsFromDb updates animalList when getAllAnimals emits success`() = runBlockingTest {
//        // Arrange
//        val animals = listOf(
//            Animal(
//                name = "Cat",
//                birthday = LocalDate.of(2019, 5, 12),
//                supplier = User("John Doe", "john@example.com", null),
//                animalCategory = AnimalCategory("Mammals"),
//                description = "Meow",
//                color = Color("Black"),
//                imageFilePath = "path/to/image.jpg",
//                isMale = true,
//                weight = 5.6f,
//                isFavorite = false
//            ),
//            Animal(
//                name = "Dog",
//                birthday = LocalDate.of(2018, 9, 3),
//                supplier = User("Jane Smith", "jane@example.com", null),
//                animalCategory = AnimalCategory("Mammals"),
//                description = "Woof",
//                color = Color("Brown"),
//                imageFilePath = null,
//                isMale = false,
//                weight = 10.2f,
//                isFavorite = true
//            )
//        )
//
//        val dbOp = AsyncOperation.success("Animals loaded", animals)
//
//        // Mock the getAllAnimals flow to emit the dbOp value
//        coEvery { getAllAnimals() } returns flowOf(dbOp)
//
//        // Act
//        viewModel.getAnimalsFromDb()
//
//        // Assert
//        assertEquals(animals, viewModel.animalList.value)
//    }
//
//    @Test
//    fun `getAnimalsFromDb does not update animalList when getAllAnimals does not emit success`() =
//        runBlockingTest {
//            // Arrange
//            val dbOp = AsyncOperation.error("Failed to load animals")
//
//            // Mock the getAllAnimals flow to emit the dbOp value
//            coEvery { getAllAnimals() } returns flowOf(dbOp)
//
//            // Act
//            viewModel.getAnimalsFromDb()
//
//            // Assert
//            assertEquals(emptyList<Animal>(), viewModel.animalList.value)
//        }
//
//    @Test
//    fun `getLoggedInUser updates user when getLoggedInUserFromDataStoreAndDatabase emits success`() =
//        runBlockingTest {
//            // Arrange
//            val user = User("John Doe", "john@example.com", null)
//            val dbOp = AsyncOperation.success("User loaded", user)
//
//            // Mock the getLoggedInUserFromDataStoreAndDatabase flow to emit the dbOp value
//            coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(dbOp)
//
//            // Act
//            viewModel.getLoggedInUser()
//
//            // Assert
//            assertEquals(user, viewModel.user.value)
//        }
//
//    @Test
//    fun `getLoggedInUser does not update user when getLoggedInUserFromDataStoreAndDatabase does not emit success`() =
//        runBlockingTest {
//            // Arrange
//            val dbOp = AsyncOperation.error("Failed to load user")
//
//            // Mock the getLoggedInUserFromDataStoreAndDatabase flow to emit the dbOp value
//            coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(dbOp)
//
//            // Act
//            viewModel.getLoggedInUser()
//
//            // Assert
//            assertEquals(null, viewModel.user.value)
//        }
//
//    @Test
//    fun `getAge returns the correct age string`() {
//        // Arrange
//        val currentDate = LocalDate.of(2023, 7, 8)
//        val birthday = LocalDate.of(1990, 1, 1)
//        val expectedAge = "33 years"
//
//        // Act
//        val age = viewModel.getAge(birthday)
//
//        // Assert
//        assertEquals(expectedAge, age)
//    }
}
