package de.fhe.adoptapal.ui.screens.register

import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class RegisterScreenViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    private lateinit var insertUserAsyncUseCase: InsertUserAsync

    @Mock
    private lateinit var navigationManager: NavigationManager

    @Before
    fun setup() {
        insertUserAsyncUseCase = mock(InsertUserAsync::class.java)
    }

    @Test
    fun addUser_validInputs_navigatesToLogin() = testScope.runBlockingTest {
        // Arrange
        val viewModel = RegisterScreenViewModel(insertUserAsyncUseCase, navigationManager)
        val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())
        viewModel.saveFeedbackFlow = saveFeedbackFlow

        val userName = "John Doe"
        val userEmail = "john.doe@example.com"
        val userPhoneNumber = "1234567890"
        val newUser = User(userName, userEmail, userPhoneNumber, null)

        val successOperation = AsyncOperation.success("User created", 1L)
        `when`(insertUserAsyncUseCase(newUser)).thenReturn(flowOf(successOperation))

        // Act
        viewModel.addUser(userName, userEmail, userPhoneNumber)

        // Assert
        assertEquals(successOperation, saveFeedbackFlow.value)
        assertEquals(Screen.Login.navigationCommand(), viewModel.navigationManager.commands.value)
    }

    @Test
    fun addUser_invalidInputs_setsErrorFeedback() = testScope.runBlockingTest {
        // Arrange
        val viewModel = RegisterScreenViewModel(insertUserAsyncUseCase, navigationManager)
        val saveFeedbackFlow = MutableStateFlow(AsyncOperation.undefined())
        viewModel.saveFeedbackFlow = saveFeedbackFlow

        val userName = ""
        val userEmail = ""
        val userPhoneNumber = ""
        val expectedErrorOperation = AsyncOperation.error("User name, email and phone are missing")

        // Act
        viewModel.addUser(userName, userEmail, userPhoneNumber)

        // Assert
        assertEquals(expectedErrorOperation, saveFeedbackFlow.value)
    }
}
