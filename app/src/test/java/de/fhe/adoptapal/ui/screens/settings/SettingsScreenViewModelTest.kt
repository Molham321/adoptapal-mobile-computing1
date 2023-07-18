package de.fhe.adoptapal.ui.screens.settings

import org.junit.Assert.*

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.fhe.adoptapal.domain.*
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsScreenViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SettingsScreenViewModel
    private var updateUserAsyncUseCase: UpdateUserAsync = mockk(relaxed = true)
    private var navigationManager: NavigationManager = mockk(relaxed = true)
    private var getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = SettingsScreenViewModel(updateUserAsyncUseCase, navigationManager, getLoggedInUserFromDataStoreAndDatabase)
    }

    @Test
    fun `invoke should update user`() = runBlockingTest {
        // Arrange
        val userName = "John Doe"
        val userEmail = "john.doe@example.com"
        val userPhoneNumber = "1234567890"
        val user = User(userName, userEmail, userPhoneNumber, null)
        val successOperation = AsyncOperation.success("Updated user with id ${user.id}", user.id)

        coEvery { updateUserAsyncUseCase(user) } returns flowOf(successOperation)

        // Act
        viewModel.updateUser(user)

        // Assert
        assertEquals(successOperation, viewModel.saveFeedbackFlow.value)
    }

    @Test
    fun `updateUser should emit error when save fails`() = runBlockingTest {
        // Arrange
        val userName = "John Doe"
        val userEmail = "john.doe@example.com"
        val userPhoneNumber = "1234567890"
        val updatedUser = User(userName, userEmail, userPhoneNumber, null)
        val errorOperation = AsyncOperation.error("Failed to update user")

        coEvery { updateUserAsyncUseCase(updatedUser) } returns flowOf(errorOperation)

        // Act
        viewModel.updateUser(updatedUser)

        // Assert
        assertEquals(errorOperation, viewModel.saveFeedbackFlow.value)
    }
}