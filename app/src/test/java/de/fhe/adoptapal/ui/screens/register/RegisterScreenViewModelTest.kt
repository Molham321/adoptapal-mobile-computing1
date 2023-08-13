package de.fhe.adoptapal.ui.screens.register

import de.fhe.adoptapal.core.LoggerFactory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.InsertUserAsync
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
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterScreenViewModelTest {
    private lateinit var viewModel: RegisterScreenViewModel
    private var insertUserAsyncUseCase: InsertUserAsync = mockk(relaxed = true)
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore = mockk(relaxed = true)
    private var navigationManager: NavigationManager = mockk(relaxed = true)
    private val loggerFactory: LoggerFactory = mockk(relaxed = true)


    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = RegisterScreenViewModel(insertUserAsyncUseCase, setLoggedInUserInDataStore, navigationManager, loggerFactory)
    }

    @Test
    fun `addUser should save user`() = runBlockingTest {
        // Arrange
        val userName = "John Doe"
        val userEmail = "john.doe@example.com"
        val userPhoneNumber = "1234567890"
        val newUser = User(userName, userEmail, userPhoneNumber, null)
        val successOperation = AsyncOperation.saving("Saving user now!")

        coEvery { insertUserAsyncUseCase(newUser) } returns flowOf(successOperation)

        // Act
        viewModel.addUser(userName, userEmail)

        // Assert
        assertEquals(successOperation, viewModel.saveFeedbackFlow.value)
    }

    @Test
    fun `addUser should emit error when required fields are missing`() = runBlockingTest {
        // Arrange
        val userName = ""
        val userEmail = ""
        val expectedError = AsyncOperation.error("Name or Email missing")

        // Act
        viewModel.addUser(userName, userEmail)

        // Assert
        assertEquals(expectedError, viewModel.saveFeedbackFlow.value)
    }
}
