package de.fhe.adoptapal.domain

import kotlinx.coroutines.flow.Flow

interface Logger {
    fun error(message: String)
    fun info(message: String)

    fun error(component: String, message: String)
    fun info(component: String, message: String)
}

interface LocalStore {
    suspend fun save(key: String, value: String)
    suspend fun load(key: String): String
}

interface NetworkController {
    fun getLatLongFromAddress(address: Address): Flow<Address>
}


interface Repository {
    // Animals
    fun getAllAnimals(): Flow<List<Animal>>
    fun getAllFavoriteAnimals(): Flow<List<Animal>>
    fun getAnimalsByRange(location: Location, range: Double): Flow<List<Animal>>
    suspend fun getAnimal(animalId: Long): Animal?
    suspend fun insertAnimal(animal: Animal): Long
    suspend fun updateAnimal(animalToUpdate: Animal) : Long
    suspend fun deleteAnimal(animal: Animal)


    // Colors
    fun getAllColors(): Flow<List<Color>>
    suspend fun insertColor(color: Color): Long
    suspend fun getColor(colorId: Long): Color?

    // AnimalCategory
    fun getAllAnimalCategories(): Flow<List<AnimalCategory>>
    suspend fun insertAnimalCategory(animalCategory: AnimalCategory): Long
    suspend fun getAnimalCategory(animalCategoryId: Long): AnimalCategory?


    // User
    fun getAllUsers(): Flow<List<User>>
    fun getUsersByRange(location: Location, range: Double): Flow<List<User>>
    suspend fun getUser(userId: Long): User?
    suspend fun getUserByEmail(userEmail: String): User?
    suspend fun insertUser(user: User): Long
    suspend fun updateUser(user: User): Long

    suspend fun deleteAllUsers()

    // Address
    suspend fun getAddress(addressId: Long): Address?
    suspend fun insertAddress(address: Address): Long

    // Rating
    suspend fun getRating(ratingId: Long): Rating?
    suspend fun insertRating(rating: Rating): Long
    suspend fun getAllRatingsBySupplierIdAsFlow(supplierId: Long): Flow<List<Rating>>
    suspend fun getAllRatingsBySeekerIdAsFlow(seekerId: Long): Flow<List<Rating>>

}