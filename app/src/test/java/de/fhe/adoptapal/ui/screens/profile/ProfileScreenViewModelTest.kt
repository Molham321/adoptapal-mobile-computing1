package de.fhe.adoptapal.ui.screens.profile

import android.util.Log
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.GetAllFavoriteAnimalsAsync
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
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
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileScreenViewModelTest {

    private lateinit var viewModel: ProfileScreenViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val getLoggedInUserFromDataStoreAndDatabase: GetLoggedInUserFromDataStoreAndDatabase =
        mockk(relaxed = true)
    private val getAllFavoriteAnimalsAsync: GetAllFavoriteAnimalsAsync = mockk(relaxed = true)

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = ProfileScreenViewModel(
            navigationManager,
            getLoggedInUserFromDataStoreAndDatabase,
            getAllFavoriteAnimalsAsync
        )
    }

    @Test
    fun `getUser should update user when user is available`() = runBlockingTest {
        // Arrange
        val user = User("John Doe", "john@example.com", null)
        coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(
            AsyncOperation.success(
                payload = user
            )
        )

        // Act
        viewModel.getUser()

        // Assert
        assertEquals(user, viewModel.user.value)
    }

// todo: was ist wenn user nicht gefunden?

//    @Test
//    fun `getUser should do nothing when user is not available`() = runBlockingTest {
//        // Arrange
//        coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(error("No logged in user found"))
//
//        // Act
//        viewModel.getUser()
//
//        // Assert
//        assertEquals(null, viewModel.user.value)
//    }

    @Test
    fun `reload should call getUser to update user`() = runBlockingTest {
        // Arrange
        val user = User("John Doe", "john@example.com", null)
        coEvery { getLoggedInUserFromDataStoreAndDatabase() } returns flowOf(
            AsyncOperation.success(
                payload = user
            )
        )

        // Act
        viewModel.reload()

        // Assert
        assertEquals(user, viewModel.user.value)
    }
}