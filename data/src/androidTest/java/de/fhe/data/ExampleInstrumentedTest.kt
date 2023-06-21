package de.fhe.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.fhe.adoptapal.data.AddressModelDao
import de.fhe.adoptapal.data.AnimalCategoryModelDao
import de.fhe.adoptapal.data.AnimalModelDao
import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.ColorModelDao
import de.fhe.adoptapal.data.RatingModelDao
import de.fhe.adoptapal.data.RepositoryImpl
import de.fhe.adoptapal.data.RequestModelDao
import de.fhe.adoptapal.data.UserModelDao
import de.fhe.adoptapal.data.fromDomain
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {



    private lateinit var userModelDao: UserModelDao
    private lateinit var addressModelDao: AddressModelDao
    private lateinit var ratingModelDao: RatingModelDao
    private lateinit var animalModelDao: AnimalModelDao
    private lateinit var animalCategoryModelDao: AnimalCategoryModelDao
    private lateinit var colorModelDao: ColorModelDao
    private lateinit var requestModelDao: RequestModelDao

    private lateinit var db: AppDatabase
    private lateinit var repository: Repository




    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userModelDao = db.userModelDao()
        addressModelDao = db.addressModelDao()
        ratingModelDao = db.ratingModelDao()
        animalModelDao = db.animalModelDao()
        animalCategoryModelDao = db.animalCategoryDao()
        colorModelDao = db.colorModelDao()
        requestModelDao = db.requestModelDao()
        repository = RepositoryImpl(userModelDao, addressModelDao, ratingModelDao, animalModelDao, animalCategoryModelDao, colorModelDao, requestModelDao)
    }

    @After
    @Throws( IOException::class )
    fun closeDb() {
        db.close()
    }


    @Test
    fun testGetAndDeleteUser() = runBlocking {

        assertTrue( "DB should start empty", userModelDao.getAll().isEmpty() )

        val userId = userModelDao.upsert(User("Name", "name@email.com", null, null).fromDomain())
        assertTrue( "DB should contain one entry", userModelDao.getAll().size == 1)

        val loadedEntity = userModelDao.get( userId  )
        assertNotNull("Loaded user entry should not be null", loadedEntity )

        userModelDao.delete( loadedEntity!! )
        assertNull( "DB should not contain deleted Entity", userModelDao.get( userId ) )
        assertTrue( "DB should be empty after deletion", userModelDao.getAll().isEmpty())
    }


    @Test
    fun testGetUserByEmail() = runBlocking {

        assertTrue( "DB should start empty", userModelDao.getAll().isEmpty() )

        val userEmail = "user@mail.com"
        val userId = userModelDao.upsert(User("Name", userEmail, null, null).fromDomain())
        assertTrue( "DB should contain one entry", userModelDao.getAll().size == 1)

        val loadedEntity = userModelDao.getUserByEmail( userEmail )
        assertNotNull("Loaded user with email $userEmail should not be null", loadedEntity )

        userModelDao.delete( loadedEntity!! )
        assertNull( "DB should not contain deleted Entity", userModelDao.get( userId ) )
        assertTrue( "DB should be empty after deletion", userModelDao.getAll().isEmpty())
    }

}