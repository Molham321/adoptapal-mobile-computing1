package de.fhe.adoptapal

import android.util.Log
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Rating
import de.fhe.adoptapal.domain.RatingEnum
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Class to create initial Test Data when the application starts
 */
class DBInitialData : KoinComponent {


    private val repo by inject<Repository>()
//    private val logger by inject<Logger>()

    private val logTag = "DB-TEST"
    fun run() {
        val dbScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        dbScope.launch {

            Log.i(logTag, "Koin DB-Test started")

            repo.deleteAllUsers()

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
            val address1 = Address(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "17",
                street = "AltonaerStraße",
                city = "Erfurt",
                zipCode = "99085",
                latitude = 50.985522,
                longitude = 11.038992
            )

            val address2 = Address(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "10",
                street = "Erfurter Straße",
                city = "Weimar",
                zipCode = "99423",
                latitude = 50.980106,
                longitude = 11.322003
            )

            repo.insertAddress(address1)
            repo.insertAddress(address2)

            // create user
            val user1 = User(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Hans Meyer",
                email = "hans.meyer@fakemail.io",
                address = address1,
                phoneNumber = null,
                useCoarseLocation = true
            )
            val user2 = User(
                id = 2,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Gabi Schnitzler",
                email = "gabi.schnitzler@tierheim.de",
                address = address2,
                phoneNumber = null,
                useCoarseLocation = false
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
                    10.5f,
                    true
                )
            )


            val rating = Rating(
                id = 0,
                createdTimestamp = LocalDateTime.now(),
                lastChangeTimestamp = LocalDateTime.now(),
                seeker = user1,
                supplier = user2,
                rating = RatingEnum.FOUR_STARS,
                comment = "Schöne Tiere, Mitarbeiter sind aber nicht nett",
            )

            repo.insertRating(rating)



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