
package de.fhe.adoptapal.ui.screens.login

import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.LocalStore
import de.fhe.adoptapal.domain.LocalStoreKey
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserByEmailAsync(private val repository: Repository) {
    operator fun invoke(userEmail: String): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading user with email $userEmail"))
        val user = repository.getUserByEmail(userEmail)
        if (user != null) {
            emit(AsyncOperation.success("Successfully loaded user with email $userEmail", user))
        } else {
            emit(AsyncOperation.error("Failed to load user with email $userEmail"))
        }
    }
}

class SetLoggedInUserInDataStore(private val localStore: LocalStore) {
    operator fun invoke(userId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.saving("Saving userId: $userId in local store"))
        localStore.save(LocalStoreKey.LOGGED_IN_USER_ID.name, userId.toString())
        emit(AsyncOperation.success("Saved userId: $userId in local store"))
    }
}

@ExperimentalCoroutinesApi
class GetUserByEmailAsyncTest {
    private lateinit var getUserByEmailAsync: GetUserByEmailAsync
    private lateinit var setLoggedInUserInDataStore: SetLoggedInUserInDataStore
    private lateinit var repository: Repository
    private lateinit var localStore: LocalStore

    @Before
    fun setup() {
        // Erstelle ein Mock-Objekt für das Repository und die LocalStore-Klasse
        repository = mockk(relaxed = true)
        localStore = mockk(relaxed = true)

        // Initialisiere die Klassen, die getestet werden sollen, mit den Mock-Objekten
        getUserByEmailAsync = GetUserByEmailAsync(repository)
        setLoggedInUserInDataStore = SetLoggedInUserInDataStore(localStore)
    }

    @Test
    fun `invoke with user emits loading, success, and saving`() = runBlockingTest {
        // Arrange
        val newUser = User("John Doe", "john@example.com", "1234567890", null)
        val userId = 123L

        // Mocke die Funktion getUserByEmail des Repositories, um den Benutzer zurückzugeben
        coEvery { repository.getUserByEmail(newUser.email) } returns newUser

        // Act
        val result = mutableListOf<AsyncOperation>()
        // Sammle die emittierten Werte aus dem getUserByEmailAsync-Flow
        getUserByEmailAsync(newUser.email).collect { result.add(it) }

        // Assert
        // Überprüfe, dass der Flow zwei AsyncOperation-Werte emittiert: loading und success
        assertEquals(2, result.size)
        assertEquals(
            AsyncOperation.loading("Start loading user with email ${newUser.email}"),
            result[0]
        )
        assertEquals(
            AsyncOperation.success(
                "Successfully loaded user with email ${newUser.email}",
                newUser
            ), result[1]
        )
    }

    @Test
    fun `invoke with non-existing user email emits loading and error`() = runBlockingTest {
        // Arrange
        val userEmail = "john@example.com"

        // Mocke die Funktion getUserByEmail des Repositories, um null zurückzugeben (Benutzer nicht gefunden)
        coEvery { repository.getUserByEmail(userEmail) } returns null

        // Act
        val result = mutableListOf<AsyncOperation>()
        // Sammle die emittierten Werte aus dem getUserByEmailAsync-Flow
        getUserByEmailAsync(userEmail).collect { result.add(it) }

        // Assert
        // Überprüfe, dass der Flow zwei AsyncOperation-Werte emittiert: loading und error
        assertEquals(2, result.size)
        assertEquals(AsyncOperation.loading("Start loading user with email $userEmail"), result[0])
        assertEquals(AsyncOperation.error("Failed to load user with email $userEmail"), result[1])
    }

    @Test
    fun `setLoggedInUserInDataStore emits saving and success`() = runBlockingTest {
        // Arrange
        val userId = 123L

        // Act
        val result = mutableListOf<AsyncOperation>()
        // Sammle die emittierten Werte aus dem setLoggedInUserInDataStore-Flow
        setLoggedInUserInDataStore(userId).collect { result.add(it) }

        // Assert
        // Überprüfe, dass der Flow zwei AsyncOperation-Werte emittiert: saving und success
        assertEquals(2, result.size)
        assertEquals(AsyncOperation.saving("Saving userId: $userId in local store"), result[0])
        assertEquals(AsyncOperation.success("Saved userId: $userId in local store"), result[1])
    }
}
