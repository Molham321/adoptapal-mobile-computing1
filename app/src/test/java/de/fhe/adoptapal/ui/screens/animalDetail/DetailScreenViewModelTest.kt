package de.fhe.adoptapal.ui.screens.animalDetail

import de.fhe.adoptapal.domain.*
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.Month

@ExperimentalCoroutinesApi
class DetailScreenViewModelTest {

    private lateinit var viewModel: DetailScreenViewModel
    private var navigationManager: NavigationManager = mockk(relaxed = true)
    private var getAnimalAsync: GetAnimalAsync = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = DetailScreenViewModel(navigationManager, 1L, getAnimalAsync)
    }

    @Test
    fun `getAnimalFromDb should update animal and dbOp state`() = runBlockingTest {
        // Arrange
        val animal = Animal(
            name = "Dog",
            birthday = LocalDate.of(2019, 5, 1),
            supplier = User("John Doe", "john@example.com", null),
            animalCategory = AnimalCategory("Dog"),
            description = "Friendly dog",
            color = Color("Brown"),
            imageFilePath = null,
            isMale = true,
            weight = 10.5f
        )
        val successOperation = AsyncOperation.success("Async Op Successful", animal)
        coEvery { getAnimalAsync.invoke(animal.id) } returns flowOf(successOperation)

        // Act
        viewModel.getAnimalFromDb(animal.id)

        // Assert
        assertEquals(animal, viewModel.animal.value)
        assertEquals(successOperation, viewModel.dbOp.value)
    }

    @Test
    fun `getAge should calculate the correct age string`() {
        // Arrange
        val birthday = LocalDate.of(2019, Month.JANUARY, 1)
        val currentDate = LocalDate.of(2023, Month.JULY, 18)
        val expectedAge = "4 years"

        // Act
        val age = viewModel.getAge(birthday)

        // Assert
        assertEquals(expectedAge, age)
    }
}
