package de.fhe.adoptapal.ui.screens.settings

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.fhe.adoptapal.core.LoggerFactory
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.GetLatLongForAddress
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
    private var getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase =
        mockk(relaxed = true)
    private val getLatLongForAddress: GetLatLongForAddress = mockk(relaxed = true)
    private val loggerFactory: LoggerFactory = mockk(relaxed = true)


    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        viewModel = SettingsScreenViewModel(
            updateUserAsyncUseCase,
            navigationManager,
            getLoggedInUserFromDataStoreAndDatabase,
            getLatLongForAddress,
            loggerFactory
        )
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
        advanceUntilIdle() // Wait for all coroutines to complete
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