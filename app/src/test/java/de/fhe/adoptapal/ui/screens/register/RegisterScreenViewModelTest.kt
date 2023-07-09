import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.domain.UserEmailUniqueException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InsertUserAsync(private val repository: Repository) {
    // The 'invoke' function allows using the class instance as a function call
    // It takes a 'newUser' parameter and returns a Flow of AsyncOperation
    suspend operator fun invoke(newUser: User): Flow<AsyncOperation> = flow {
        // Emit a loading AsyncOperation to indicate the start of user creation
        emit(AsyncOperation.loading("Start creating user..."))
        try {
            // Call the repository to insert the new user
            val userId = repository.insertUser(newUser)
            // Emit a success AsyncOperation with the user ID
            emit(AsyncOperation.success("Created user with id $userId", userId))
        } catch (ex: UserEmailUniqueException) {
            // If the repository throws a UserEmailUniqueException, emit an error AsyncOperation
            emit(AsyncOperation.error("Failed to save user because email ${ex.email} already exists"))
        }
    }
}

@ExperimentalCoroutinesApi
class InsertUserAsyncTest {
    private lateinit var insertUserAsync: InsertUserAsync
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        insertUserAsync = InsertUserAsync(repository)
    }

    @Test
    fun `invoke with new user emits loading and success`() = runBlockingTest {
        // Arrange
        val newUser = User("John Doe", "john@example.com", "1234567890", null)
        val userId = 123L

        // Mock the repository's insertUser function to return the user ID
        coEvery { repository.insertUser(newUser) } returns userId

        // Act
        val result = mutableListOf<AsyncOperation>()
        // Collect the emitted values from the insertUserAsync flow
        insertUserAsync(newUser).collect { result.add(it) }

        // Assert
        // Verify that the flow emitted two AsyncOperation values: loading and success
        assertEquals(2, result.size)
        assertEquals(AsyncOperation.loading("Start creating user..."), result[0])
        assertEquals(AsyncOperation.success("Created user with id $userId", userId), result[1])
    }

    @Test
    fun `invoke with user email already exists emits loading and error`() = runBlockingTest {
        // Arrange
        val newUser = User("John Doe", "john@example.com", "1234567890", null)
        val userEmail = "john@example.com"
        val expectedError = "Failed to save user because email $userEmail already exists"

        // Mock the repository's insertUser function to throw a UserEmailUniqueException
        coEvery { repository.insertUser(newUser) } throws UserEmailUniqueException(userEmail)

        // Act
        val result = mutableListOf<AsyncOperation>()
        // Collect the emitted values from the insertUserAsync flow
        insertUserAsync(newUser).collect { result.add(it) }

        // Assert
        // Verify that the flow emitted two AsyncOperation values: loading and error
        assertEquals(2, result.size)
        assertEquals(AsyncOperation.loading("Start creating user..."), result[0])
        assertEquals(AsyncOperation.error(expectedError), result[1])
    }
}