package de.fhe.adoptapal.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    // Animals
    fun getAllAnimals(): Flow<List<Animal>>
    suspend fun insertAnimal(animal: Animal): Long

    // Colors
    fun getAllColors(): Flow<List<Color>>
    suspend fun insertColor(color: Color): Long
    suspend fun getColor(colorId: Long): Color?

    // AnimalCategory
    fun getAllAnimalCategories(): Flow<List<AnimalCategory>>
    suspend fun insertAnimalCategory(animalCategory: AnimalCategory): Long
    suspend fun getAnimalCategory(animalCategoryId: Long): AnimalCategory?


    // User
    suspend fun getUser(userId: Long): User?
    suspend fun insertUser(user: User): Long

    // Address
    suspend fun getAddress(addressId: Long): Address?
    suspend fun insertAddress(address: Address): Long
}