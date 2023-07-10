package de.fhe.adoptapal.domain;

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


// ----------------
// Animals
// ----------------
class GetAllAnimals(private val repository: Repository) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading animals..."))
        repository.getAllAnimals().collect() {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class GetAnimalByRangeAsync(private val repository: Repository) {
    operator fun invoke(location: Location, distance: Double): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading animals by range..."))
        repository.getUsersByRange(location, distance).collect {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class CreateAnimalAsync(private val repository: Repository) {
    operator fun invoke(newAnimal: Animal): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating animal..."))
        val animalId = repository.insertAnimal(newAnimal)
        emit(AsyncOperation.success("Animal saved with ID $animalId", animalId))
    }
}

class GetAnimalAsync(private val repository: Repository) {
    operator fun invoke(animalId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Loading animal with id $animalId"))
        val animal = repository.getAnimal(animalId)
        if (animal != null) {
            emit(AsyncOperation.success("Loaded animal with id $animalId", animal))
        } else {
            emit(AsyncOperation.error("Failed to load animal with id $animalId"))
        }
    }
}

class GetAllFavoriteAnimalsAsync(private val repository: Repository) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading favorite animals..."))
        repository.getAllFavoriteAnimals().collect() {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class DeleteAnimalAsync(private val repository: Repository) {
    operator fun invoke(animal: Animal): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Deleting animal with id ${animal.id}"))
        repository.deleteAnimal(animal)
        emit(AsyncOperation.success("Deleted animal with id ${animal.id}"))
    }
}


// ----------------
// COLORS
// ----------------
class GetAllColors(private val repository: Repository) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading colors..."))
        repository.getAllColors().collect() {
            emit(AsyncOperation.success("Colors loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class CreateColorAsync(private val repository: Repository) {
    operator fun invoke(newColor: Color): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating color..."))
        val colorId = repository.insertColor(newColor)
        emit(AsyncOperation.success("Color saved with ID $colorId", colorId))
    }
}

class GetColorAsync(private val repository: Repository) {
    operator fun invoke(colorId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading color with id $colorId"))
        val color = repository.getColor(colorId)
        if (color != null) {
            emit(AsyncOperation.success("Color loaded with id $colorId", color))
        } else {
            emit(AsyncOperation.error("Failed to load color with id $colorId"))
        }
    }
}

// ----------------
// Animal Category
// ----------------
class GetAllAnimalCategories(private val repository: Repository) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading animalCategories..."))
        repository.getAllAnimalCategories().collect() {
            emit(AsyncOperation.success("AnimalCategories loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class CreateAnimalCategoryAsync(private val repository: Repository) {
    operator fun invoke(newAnimalCategory: AnimalCategory): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating animalCategory..."))
        val animalCategoryId = repository.insertAnimalCategory(newAnimalCategory)
        emit(
            AsyncOperation.success(
                "AnimalCategory saved with ID $animalCategoryId",
                animalCategoryId
            )
        )
    }
}

class GetAnimalCategoryAsync(private val repository: Repository) {
    operator fun invoke(animalCategoryId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading animalCategory with id $animalCategoryId"))
        val animalCategory = repository.getAnimalCategory(animalCategoryId)
        if (animalCategory != null) {
            emit(
                AsyncOperation.success(
                    "AnimalCategory loaded with id $animalCategoryId",
                    animalCategory
                )
            )
        } else {
            emit(AsyncOperation.error("Failed to load animalCategory with id $animalCategoryId"))
        }
    }
}

// ----------------
// User
// ----------------
class GetAllUsers(private val repository: Repository) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading users..."))
        repository.getAllUsers().collect {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class GetUsersByRangeAsync(private val repository: Repository) {
    operator fun invoke(location: Location, distance: Double): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading users by range..."))
        repository.getUsersByRange(location, distance).collect {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class GetUserAsync(private val repository: Repository) {
    operator fun invoke(userId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading user with id $userId"))
        val user = repository.getUser(userId)
        if (user != null) {
            emit(AsyncOperation.success("Successfully loaded user with id $userId", user))
        } else {
            emit(AsyncOperation.error("Failed to load user with id $userId"))
        }
    }
}


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

class InsertUserAsync(private val repository: Repository) {
    operator fun invoke(newUser: User): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating user..."))
        try {
            val userId = repository.insertUser(newUser)
            emit(AsyncOperation.success("Created user with id $userId", userId))
        } catch (ex: UserEmailUniqueException) {
            emit(AsyncOperation.error("Failed to save user because email ${ex.email} already exists"))
        }
    }
}

class UpdateUserAsync(private val repository: Repository) {
    operator fun invoke(user: User): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start updating user with id: ${user.id} ..."))
        try {
            val userId = repository.updateUser(user)
            emit(AsyncOperation.success("Updated user with id $userId", userId))
        } catch (ex: UserEmailUniqueException) {
            emit(AsyncOperation.error("Failed to save user because email ${ex.email} already exists"))
        }
    }
}


// ----------------
// Address
// ----------------
class GetAddressAsync(private val repository: Repository) {
    operator fun invoke(addressId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading address with id $addressId"))
        val address = repository.getAddress(addressId)
        if (address != null) {
            emit(AsyncOperation.success("Successfully loaded address with id $addressId", address))
        } else {
            emit(AsyncOperation.error("Failed to load address with id $addressId"))
        }
    }
}

class InsertAddressAsync(private val repository: Repository) {
    operator fun invoke(address: Address): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start saving address..."))
        val addressId = repository.insertAddress(address)
        emit(AsyncOperation.success("Created address with id $addressId", addressId))
    }
}

// ----------------
// Rating
// ----------------

class GetRatingAsync(private val repository: Repository) {
    operator fun invoke(ratingId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading rating with id $ratingId"))
        val rating = repository.getRating(ratingId)
        if (rating != null) {
            emit(AsyncOperation.success("Successfully loaded rating with id $ratingId", rating))
        } else {
            emit(AsyncOperation.error("Failed to load rating with id $ratingId"))
        }
    }
}

class InsertRatingAsync(private val repository: Repository) {
    operator fun invoke(rating: Rating): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start saving rating..."))
        val ratingId = repository.insertRating(rating)
        emit(AsyncOperation.success("Created rating with id $ratingId", rating))
    }
}

class GetAllRatingsBySeekerId(private val repository: Repository) {
    operator fun invoke(seekerId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading ratings for seeker with id $seekerId..."))
        repository.getAllRatingsBySeekerIdAsFlow(seekerId).collect {
            emit(AsyncOperation.success("Ratings loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class GetAllRatingsBySupplierId(private val repository: Repository) {
    operator fun invoke(supplierId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading ratings for supplier with id $supplierId..."))
        repository.getAllRatingsBySupplierIdAsFlow(supplierId).collect {
            emit(AsyncOperation.success("Ratings loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}


// ----------------
// Datastore
// ----------------


/**
 * get logged in user
 *
 * @return success - userId was found in Storage and user with the userId from the database is returned
 * @return error - userId not found or no user with that userId is in the database
 */
class GetLoggedInUserFromDataStoreAndDatabase(
    private val localStore: LocalStore,
    private val repository: Repository
) {
    operator fun invoke(): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading logged in user from localStore..."))

        // get userId from datastore
        val result = localStore.load(LocalStoreKey.LOGGED_IN_USER_ID.name)
        if (!result.isBlank()) {
            val user = repository.getUser(result.toLong())
            if (user != null) {
                emit(AsyncOperation.success("User found and loaded", user))
                return@flow
            }
        }
        emit(AsyncOperation.error("No logged in user found"))
    }
}

class SetLoggedInUserInDataStore(private val localStore: LocalStore) {
    operator fun invoke(userId: Long): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.saving("Saving userId: $userId in local store"))
        localStore.save(LocalStoreKey.LOGGED_IN_USER_ID.name, userId.toString())
        emit(AsyncOperation.success("Saved userId: $userId in local store"))
    }
}


// ----------------
// Network
// ----------------

class GetLatLongForAddress(private val networkController: NetworkController) {
    operator fun invoke(address: Address): Flow<AsyncOperation> = flow {
        emit(AsyncOperation.saving("Start getting LatLong data for address"))
        var updatedAddress: Address? = null
        networkController.getLatLongFromAddress(address).collect {
            updatedAddress = it
        }
        emit(
            AsyncOperation.success(
                "Successfully requested latLong for address",
                updatedAddress as Any
            )
        )
    }
}

