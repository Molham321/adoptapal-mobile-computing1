package de.fhe.adoptapal

import android.app.Application
import android.util.Log
import de.fhe.adoptapal.di.modules.androidCoreModule
import de.fhe.adoptapal.di.modules.databaseModule
import de.fhe.adoptapal.di.modules.viewModelModule
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import java.time.LocalDate
import java.time.LocalDateTime

class Bootstrap : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // TODO: maybe add a logger here
            androidContext(this@Bootstrap)

            // modules
            modules(databaseModule)
            modules(androidCoreModule)
            modules(viewModelModule)
        }

        // test Database
        DbTest().run()
    }
}


class DbTest : KoinComponent {
    private val repo by inject<Repository>()
//    private val logger by inject<Logger>()

    private val logTag = "DB-TEST"
    fun run() {
        val dbScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        dbScope.launch {

            Log.i(logTag, "Koin DB-Test started")

            // create colors
            val color1 =
                Color(1, LocalDateTime.of(2023, 5, 30, 22, 11), LocalDateTime.now(), "White")
            repo.insertColor(color1)
            repo.insertColor(
                Color(
                    2,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Red"
                )
            )


            // create animal categories
            val animalCategory1 =
                AnimalCategory(1, LocalDateTime.of(2023, 5, 30, 22, 11), LocalDateTime.now(), "Cat")
            repo.insertAnimalCategory(animalCategory1)
            repo.insertAnimalCategory(
                AnimalCategory(
                    2,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Dog"
                )
            )
            repo.insertAnimalCategory(
                AnimalCategory(
                    3,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Fish"
                )
            )
            repo.insertAnimalCategory(
                AnimalCategory(
                    4,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Reptile"
                )
            )
            repo.insertAnimalCategory(
                AnimalCategory(
                    5,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Other"
                )
            )

            // create address
            val address = Address(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "17a",
                street = "AltonaerStraße",
                city = "Erfurt",
                zipCode = "55192"
            )
            repo.insertAddress(address)


            // create user
            val user1 = User(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Hans Meyer",
                email = "hans.meyer@fakemail.io",
                address = address,
                phoneNumber = null
            )
            val user2 = User(
                id = 2,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Gabi Schnitzler",
                email = "gabi.schnitzler@tierheim.de",
                address = address,
                phoneNumber = null
            )
            repo.insertUser(user1)
            repo.insertUser(user2)


            // create animal
            repo.insertAnimal(
                Animal(
                    1,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Rocky",
                    LocalDate.of(2019, 10, 20),
                    user1,
                    animalCategory1,
                    "Beschreibung des Tiers",
                    color1,
                    null,
                    false,
                    10.5f
                )
            )



            runBlocking {
                repo.getAllAnimals().collect {
                    it.forEach { animal ->
                        Log.i(logTag, animal.toString())
                    }
                }
            }

        }
    }
}
