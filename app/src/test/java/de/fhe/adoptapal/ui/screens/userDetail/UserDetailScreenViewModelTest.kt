package de.fhe.adoptapal.ui.screens.userDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.GetUserAsync
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

@ExperimentalCoroutinesApi
class UserDetailScreenViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserDetailScreenViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val userId: Long = 123L
    private var getUserAsyncUseCase: GetUserAsync = mockk(relaxed = true)
    private val getUserAnimalsAsync: GetUserAnimalsAsync = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = UserDetailScreenViewModel(
            navigationManager,
            userId,
            getUserAsyncUseCase,
            getUserAnimalsAsync
        )
    }

    @Test
    fun `getUserFromDb should retrieve user from database`() = runBlockingTest {
        // Arrange
        val userName = "John Doe"
        val userEmail = "john.doe@example.com"
        val userPhoneNumber = "1234567890"
        val user = User(userName, userEmail, userPhoneNumber, null)
        val successOperation = AsyncOperation.success("Async Op Successful", user)

        coEvery { getUserAsyncUseCase.invoke(userId) } returns flowOf(successOperation)

        // Act
        viewModel.getUserFromDb(userId)

        // Assert
        assertEquals(successOperation, viewModel.dbOp.value)
        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun `getUserFromDb should emit error when retrieval fails`() = runBlockingTest {
        // Arrange
        val expectedError = AsyncOperation.error("Failed to retrieve user")

        coEvery { getUserAsyncUseCase.invoke(userId) } returns flowOf(expectedError)

        // Act
        viewModel.getUserFromDb(userId)

        // Assert
        assertEquals(expectedError, viewModel.dbOp.value)
        assertEquals(null, viewModel.user.value)
    }
}