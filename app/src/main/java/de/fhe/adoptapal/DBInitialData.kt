package de.fhe.adoptapal

import de.fhe.adoptapal.core.LoggerFactory
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Class to create initial Test Data when the application starts
 */
class DBInitialData : KoinComponent {

    private val logger = LoggerFactory.getLogger()

    private val repo by inject<Repository>()

    private val logTag = "DB-TEST"
    fun run() {
        val dbScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        dbScope.launch {

            if (repo.getUser(1) != null) {
                logger.info(logTag, "User found, so no more example data is created")
                return@launch
            }

            logger.info(logTag, "Koin DB-Test started")

            repo.deleteAllUsers()

            // create colors
            val color1 =
                Color(1, LocalDateTime.of(2023, 5, 30, 22, 11), LocalDateTime.now(), "Weiß")
            repo.insertColor(color1)
            val color2 =
                Color(
                    2,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Schwarz"
                )
            repo.insertColor(color2)
            val color3 =
                Color(
                    3,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Rot"
                )
            repo.insertColor(color3)
            val color4 =
                Color(
                    4,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Grün"
                )
            repo.insertColor(color4)
            val color5 =
                Color(
                    5,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Orange"
                )
            repo.insertColor(color5)
            val color6 =
                Color(
                    6,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Gelb"
                )
            repo.insertColor(color6)
            val color7 =
                Color(
                    7,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Braun"
                )
            repo.insertColor(color7)
            val color8 =
                Color(
                    8,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Grau"
                )
            repo.insertColor(color8)
            val color9 =
                Color(
                    9,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Blau"
                )
            repo.insertColor(color9)
            val color10 =
                Color(
                    10,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Mehrfarbig"
                )
            repo.insertColor(color10)
            val color11 =
                Color(
                    11,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Andere"
                )
            repo.insertColor(color11)


            // create animal categories
            val animalCategory1 =
                AnimalCategory(
                    1,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Katze"
                )

            val animalCategory2 = AnimalCategory(
                2,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Hund"
            )

            val animalCategory3 = AnimalCategory(
                3,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Fisch"
            )

            val animalCategory4 = AnimalCategory(
                4,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Reptil"
            )

            val animalCategory5 = AnimalCategory(
                5,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Nagetier"
            )

            val animalCategory6 = AnimalCategory(
                6,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Vogel"
            )

            val animalCategory7 = AnimalCategory(
                7,
                LocalDateTime.of(2023, 5, 30, 22, 11),
                LocalDateTime.now(),
                "Andere"
            )
            repo.insertAnimalCategory(animalCategory1)
            repo.insertAnimalCategory(animalCategory2)
            repo.insertAnimalCategory(animalCategory3)
            repo.insertAnimalCategory(animalCategory4)
            repo.insertAnimalCategory(animalCategory5)
            repo.insertAnimalCategory(animalCategory6)
            repo.insertAnimalCategory(animalCategory7)

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
                id = 2,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "16",
                street = "Berkaer Straße",
                city = "Weimar",
                zipCode = "99425",
                latitude = 50.964435,
                longitude = 11.320641
            )

            val address3 = Address(
                id = 3,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "1",
                street = "Anger",
                city = "Erfurt",
                zipCode = "99085",
                latitude = 50.977366,
                longitude = 11.035729
            )
            val address4 = Address(
                id = 4,
                createdTimestamp = LocalDateTime.of(2023, 7, 30, 12, 23),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "100",
                street = "Artur-Becker-Straße",
                city = "Jena",
                zipCode = "07745",
                latitude = 50.873348,
                longitude = 11.590350
            )
            val address5 = Address(
                id = 5,
                createdTimestamp = LocalDateTime.of(2023, 7, 30, 12, 23),
                lastChangeTimestamp = LocalDateTime.now(),
                houseNumber = "1b",
                street = "Im Malmtal",
                city = "Rudolstadt",
                zipCode = "07407",
                latitude = 50.735930,
                longitude = 11.334090
            )

            repo.insertAddress(address1)
            repo.insertAddress(address2)
            repo.insertAddress(address3)
            repo.insertAddress(address4)
            repo.insertAddress(address5)

            // create user
            val user1 = User(
                id = 1,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Hans Meyer",
                email = "hans.meyer@fakemail.io",
                address = address1,
                phoneNumber = null,
            )
            val user2 = User(
                id = 2,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Gabi Schnitzler",
                email = "gabi.schnitzler@tierheim.de",
                address = address2,
                phoneNumber = "017801870420",
            )
            val user3 = User(
                id = 3,
                createdTimestamp = LocalDateTime.of(2023, 5, 30, 22, 11),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Richard Klöse",
                email = "richard.kloese@mail.de",
                address = address3,
                phoneNumber = "0178058270420",
            )
            val user4 = User(
                id = 4,
                createdTimestamp = LocalDateTime.of(2023, 7, 30, 12, 23),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Tierheim Katzentempel",
                email = "katzentempel@tierheim.de",
                address = address4,
                phoneNumber = "0178445578987",
            )
            val user5 = User(
                id = 5,
                createdTimestamp = LocalDateTime.of(2023, 7, 30, 12, 23),
                lastChangeTimestamp = LocalDateTime.now(),
                name = "Felix Richter",
                email = "felix.richter@email.com",
                address = address5,
                phoneNumber = "0178975312468",
            )
            repo.insertUser(user1)
            repo.insertUser(user2)
            repo.insertUser(user3)
            repo.insertUser(user4)
            repo.insertUser(user5)

            // create animal
            repo.insertAnimal(
                Animal(
                    1,
                    LocalDateTime.of(2023, 5, 30, 22, 11),
                    LocalDateTime.now(),
                    "Rocky",
                    LocalDate.of(2019, 10, 20),
                    user4,
                    animalCategory1,
                    "Rocky ist ein aufgeweckter und freundlicher Kater.",
                    color5,
                    null,
                    true,
                    4.5f,
                    false
                )
            )

            repo.insertAnimal(
                Animal(
                    2,
                    LocalDateTime.of(2023, 5, 31, 12, 30),
                    LocalDateTime.now(),
                    "Buddy",
                    LocalDate.of(2020, 3, 15),
                    user2,
                    animalCategory2,
                    "Der sanfte Riese sucht nach einem liebevollen Zuhause.",
                    color10,
                    null,
                    true,
                    8.2f,
                    false
                )
            )

            repo.insertAnimal(
                Animal(
                    3,
                    LocalDateTime.of(2023, 6, 1, 9, 45),
                    LocalDateTime.now(),
                    "Max",
                    LocalDate.of(2017, 7, 10),
                    user2,
                    animalCategory3,
                    "Max freut sich auf ein freundliches Zuhause mit vielen freundlichen Fischen.",
                    color1,
                    null,
                    false,
                    0.7f,
                    false
                )
            )

            repo.insertAnimal(
                Animal(
                    4,
                    LocalDateTime.of(2023, 6, 2, 18, 0),
                    LocalDateTime.now(),
                    "Luna",
                    LocalDate.of(2022, 1, 5),
                    user1,
                    animalCategory4,
                    "Luna ist eine Schlange mit einer wunderschönen Musterung.",
                    color10,
                    null,
                    false,
                    6.9f,
                    true
                )
            )

            repo.insertAnimal(
                Animal(
                    5,
                    LocalDateTime.of(2023, 6, 3, 15, 20),
                    LocalDateTime.now(),
                    "Charlie",
                    LocalDate.of(2021, 9, 12),
                    user2,
                    animalCategory5,
                    "Charlie der Hamstermann würde am liebsten den ganzen Tag futtern.",
                    color7,
                    null,
                    true,
                    1.1f,
                    false
                )
            )

            repo.insertAnimal(
                Animal(
                    6,
                    LocalDateTime.of(2023, 6, 4, 11, 10),
                    LocalDateTime.now(),
                    "Milo",
                    LocalDate.of(2020, 5, 8),
                    user1,
                    animalCategory6,
                    "Milo ist ein sehr redseliger Kakadu, der sich gern mit seinem Besitzer unterhält.",
                    color1,
                    null,
                    true,
                    2.3f,
                    true
                )
            )

            repo.insertAnimal(
                Animal(
                    7,
                    LocalDateTime.of(2023, 7, 30, 12, 23),
                    LocalDateTime.now(),
                    "Doris",
                    LocalDate.of(2019, 4, 22),
                    user4,
                    animalCategory1,
                    "Doris ist eine freundliche und verschmuste Dame.",
                    color10,
                    null,
                    false,
                    4.5f,
                    false
                )
            )
            repo.insertAnimal(
                Animal(
                    8,
                    LocalDateTime.of(2023, 7, 30, 12, 23),
                    LocalDateTime.now(),
                    "Jojo",
                    LocalDate.of(2021, 9, 2),
                    user4,
                    animalCategory1,
                    "Jojo ist ein verspielter Kater, der gern neugierig seine Umgebung erkundet.",
                    color8,
                    null,
                    true,
                    3.2f,
                    true
                )
            )
            repo.insertAnimal(
                Animal(
                    9,
                    LocalDateTime.of(2023, 7, 30, 12, 23),
                    LocalDateTime.now(),
                    "Törtchen",
                    LocalDate.of(2023, 5, 1),
                    user4,
                    animalCategory1,
                    "Törtchen verbringt am liebsten den ganzen Tag damit zu schlafen.",
                    color5,
                    null,
                    false,
                    1.0f,
                    true
                )
            )
            repo.insertAnimal(
                Animal(
                    10,
                    LocalDateTime.of(2023, 8, 8, 21, 21),
                    LocalDateTime.now(),
                    "Schneckobert",
                    LocalDate.of(2023, 1, 18),
                    user5,
                    animalCategory7,
                    "Ein höflicher und stets zuvorkommender Gentleman.",
                    color7,
                    null,
                    true,
                    0.2f,
                    true
                )
            )
            repo.insertAnimal(
                Animal(
                    11,
                    LocalDateTime.of(2023, 8, 8, 21, 21),
                    LocalDateTime.now(),
                    "Danny",
                    LocalDate.of(2018, 12, 4),
                    user5,
                    animalCategory2,
                    "Danny ist am liebsten draußen an warmen Sommertagen.",
                    color2,
                    null,
                    true,
                    3.4f,
                    false
                )
            )
            repo.insertAnimal(
                Animal(
                    12,
                    LocalDateTime.of(2023, 8, 8, 21, 21),
                    LocalDateTime.now(),
                    "Suzi",
                    LocalDate.of(2021, 8, 12),
                    user5,
                    animalCategory6,
                    "Das Vögelchen mit der schönsten Stimme am Morgen - das ist Suzi!",
                    color9,
                    null,
                    false,
                    0.6f,
                    false
                )
            )
            repo.insertAnimal(
                Animal(
                    13,
                    LocalDateTime.of(2023, 8, 8, 21, 21),
                    LocalDateTime.now(),
                    "Elizabeth",
                    LocalDate.of(2023, 2, 13),
                    user5,
                    animalCategory5,
                    "Elizabeth die Hasendame hat wunderschöne, weiche und lange Schlappohren.",
                    color6,
                    null,
                    false,
                    1.0f,
                    false
                )
            )
            repo.insertAnimal(
                Animal(
                    14,
                    LocalDateTime.of(2023, 6, 24, 14, 30),
                    LocalDateTime.now(),
                    "Akira",
                    LocalDate.of(2018, 5, 27),
                    user3,
                    animalCategory3,
                    "Die Schuppen von Koi Akira schimmern wirklich wunderschön in der Sonne.",
                    color3,
                    null,
                    true,
                    0.5f,
                    false
                )
            )
            repo.insertAnimal(
                Animal(
                    15,
                    LocalDateTime.of(2023, 6, 24, 14, 30),
                    LocalDateTime.now(),
                    "Lili",
                    LocalDate.of(2019, 11, 7),
                    user3,
                    animalCategory4,
                    "Bartagamendame Lili sieht fast aus wie ein kleiner Drache.",
                    color7,
                    null,
                    false,
                    0.3f,
                    false
                )
            )

            // quick test animal creation
            runBlocking {
                repo.getAllAnimals().collect {
                    it.forEach { animal ->
                        logger.info(logTag, animal.toString())
                    }
                }
            }

        }
    }

}