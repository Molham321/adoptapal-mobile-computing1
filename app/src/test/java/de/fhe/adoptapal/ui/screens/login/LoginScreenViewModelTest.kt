package de.fhe.adoptapal.ui.screens.login

import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.GetUserByEmailAsync
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
class LoginScreenViewModelTest {

    private lateinit var viewModel: LoginScreenViewModel
    private val getUserByEmailAsyncUseCase: GetUserByEmailAsync = mockk(relaxed = true)
    private val setLoggedInUserInDataStore: SetLoggedInUserInDataStore = mockk(relaxed = true)
    private val navigationManager: NavigationManager = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = LoginScreenViewModel(
            getUserByEmailAsyncUseCase,
            setLoggedInUserInDataStore,
            navigationManager
        )
    }

    @Test
    fun `login should set dbOp to success and navigate to profile when user credentials are correct`() =
        runBlockingTest {
            // Arrange
            val userEmail = "test@example.com"
            val userPassword = "password"
            val user = User("John Doe", "john@example.com", null)
            coEvery { getUserByEmailAsyncUseCase.invoke(userEmail) } returns flowOf(
                AsyncOperation.success(
                    payload = user
                )
            )

            // Act
            viewModel.login(userEmail, userPassword)

            // Assert
            assertEquals(AsyncOperationState.SUCCESS, viewModel.dbOp.value.status)
        }

    @Test
    fun `login should set saveFeedbackFlow to error when either email or password is blank`() =
        runBlockingTest {
            // Arrange
            val userEmail = ""
            val userPassword = "password"

            // Act
            viewModel.login(userEmail, userPassword)

            // Assert
            assertEquals(AsyncOperationState.ERROR, viewModel.saveFeedbackFlow.value.status)
            assertEquals(
                "please enter Email and Password",
                viewModel.saveFeedbackFlow.value.message
            )
        }

    @Test
    fun `login should set saveFeedbackFlow to error when user credentials are incorrect`() =
        runBlockingTest {
            // Arrange
            val userEmail = "test@example.com"
            val userPassword = "password"
            coEvery { getUserByEmailAsyncUseCase.invoke(userEmail) } returns flowOf(
                AsyncOperation.error(
                    "User not found"
                )
            )

            // Act
            viewModel.login(userEmail, userPassword)

            // Assert
            assertEquals(AsyncOperationState.ERROR, viewModel.saveFeedbackFlow.value.status)
            assertEquals("Email or Password not correct", viewModel.saveFeedbackFlow.value.message)
        }
}