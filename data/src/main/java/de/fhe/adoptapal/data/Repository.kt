package de.fhe.adoptapal.data

import android.util.Log
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.domain.Rating
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class RepositoryImpl(
    private val userModelDao: UserModelDao,
    private val addressModelDao: AddressModelDao,
    private val ratingModelDao: RatingModelDao,
    private val animalModelDao: AnimalModelDao,
    private val animalCategoryModelDao: AnimalCategoryModelDao,
    private val colorModelDao: ColorModelDao,
    private val requestModelDao: RequestModelDao
) : Repository {

    // ----------------
    // Animal
    // ----------------
    override fun getAllAnimals(): Flow<List<Animal>> {
        return animalModelDao.getAllAsFlow().map { animalEntityList ->
            animalEntityList.map { animalEntity ->
                getAnimalWithContent(animalEntity)
            }
        }
    }

    override fun getAllFavoriteAnimals(): Flow<List<Animal>> {
        return animalModelDao.getAllFavoriteAnimalsAsFlow().map { animalEntityList ->
            animalEntityList.map { animalEntity ->
                getAnimalWithContent(animalEntity)
            }
        }
    }

    override fun getAnimalsByRange(location: Location, range: Double): Flow<List<Animal>> {
        return this.getAllAnimals().map { animalList ->
            animalList.filter { animal ->
                animal.supplier.address?.let {
                    location.isWithinRangeOf(
                        it.latitude,
                        it.longitude,
                        range
                    )
                } ?: false
            }
        }
    }


    override suspend fun getAnimal(animalId: Long): Animal? {
        val animalEntity = animalModelDao.get(animalId)
        return if (animalEntity != null) getAnimalWithContent(animalEntity) else null
    }

    private suspend fun getAnimalWithContent(animalEntity: AnimalModel): Animal {
        // get Data for Animal
        val animalCategory = animalCategoryModelDao.get(animalEntity.animalCategoryId)
            ?.toDomain()
        val color = colorModelDao.get(animalEntity.colorId)?.toDomain()
        val supplierEntity = userModelDao.get(animalEntity.supplierId)
        val address = supplierEntity?.addressId?.let { addressModelDao.get(it)?.toDomain() }
        val supplier = supplierEntity?.toDomain(address)

        return animalEntity.toDomain(
            supplier = supplier!!,
            animalCategory = animalCategory!!,
            color = color!!
        )
    }

    override suspend fun insertAnimal(animal: Animal): Long {
        return animalModelDao.upsert(animal.fromDomain());
    }

    override suspend fun deleteAnimal(animal: Animal) {
        animalModelDao.delete(animal.fromDomain())
    }

    // ----------------
    // Colors
    // ----------------
    override fun getAllColors(): Flow<List<Color>> {
        return colorModelDao.getAllAsFlow().map { colorEntityList ->
            colorEntityList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertColor(color: Color): Long {
        return colorModelDao.upsert(color.fromDomain())
    }

    override suspend fun getColor(colorId: Long): Color? {
        return colorModelDao.get(colorId)?.toDomain()
    }

    // ----------------
    // AnimalCategory
    // ----------------
    override fun getAllAnimalCategories(): Flow<List<AnimalCategory>> {
        return animalCategoryModelDao.getAllAsFlow().map { animalCategoryEntityList ->
            animalCategoryEntityList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertAnimalCategory(animalCategory: AnimalCategory): Long {
        return animalCategoryModelDao.upsert(animalCategory.fromDomain())
    }

    override suspend fun getAnimalCategory(animalCategoryId: Long): AnimalCategory? {
        return animalCategoryModelDao.get(animalCategoryId)?.toDomain()
    }

    // ----------------
    // User
    // ----------------
    override fun getAllUsers(): Flow<List<User>> {
        return userModelDao.getAllAsFlow().map { userEntityList ->
            userEntityList.map { userEntity ->
                userEntity.toDomain(userEntity.addressId?.let { getAddress(it) })
            }
        }
    }

    override fun getUsersByRange(location: Location, distance: Double): Flow<List<User>> {
        return this.getAllUsers().map { userList ->
            userList.filter { user ->
                user.address?.let { location.isWithinRangeOf(it.latitude, it.longitude, distance) }
                    ?: false
            }
        }
    }

    override suspend fun getUser(userId: Long): User? {
        val userModel = userModelDao.get(userId)
        val addressModel = userModel?.addressId?.let { addressModelDao.get(it) }

        return userModel?.toDomain(addressModel?.toDomain())
    }

    override suspend fun getUserByEmail(userEmail: String): User? {
        val userModel = userModelDao.getUserByEmail(userEmail)
        val address = userModel?.addressId?.let { addressModelDao.get(it) }

        return userModel?.toDomain(address?.toDomain())
    }

    override suspend fun insertUser(user: User): Long {
        return userModelDao.upsert(user.fromDomain())
    }

    override suspend fun updateUser(user: User): Long {
        Log.i("Repository", "update user")
        userModelDao.get(user.id)?.let { savedEntity ->

            var addressId : Long? = null
            // update users address if exists
            if(user.address != null) {
                Log.i("Repository", "update or create user address")
                user.address?.id = addressModelDao.upsert(user.address!!.fromDomain())
            }

            // Update User
            val userEntity = user.fromDomain()
            userEntity.lastChangeTimestamp = LocalDateTime.now()
            userEntity.createdTimestamp = savedEntity.createdTimestamp
            userModelDao.upsert(userEntity)

            return user.id
        }
        return -1
    }

    override suspend fun deleteAllUsers() {
        userModelDao.deleteAll()
    }

    // ----------------
    // Address
    // ----------------
    override suspend fun getAddress(addressId: Long): Address? {
        return addressModelDao.get(addressId)?.toDomain()
    }

    override suspend fun insertAddress(address: Address): Long {
        return addressModelDao.upsert(address.fromDomain())
    }

    // ----------------
    // Rating
    // ----------------

    override suspend fun getRating(ratingId: Long): Rating? {
        val ratingModel = ratingModelDao.get(ratingId) ?: return null
        val seeker = getUser(ratingModel.seekerId) ?: return null
        val supplier = getUser(ratingModel.supplierId) ?: return null

        return ratingModel.toDomain(seeker, supplier)
    }

    override suspend fun insertRating(rating: Rating): Long {
        return ratingModelDao.upsert(rating.fromDomain())
    }

    override suspend fun getAllRatingsBySupplierIdAsFlow(supplierId: Long): Flow<List<Rating>> {
        val supplier = getUser(supplierId)
        return ratingModelDao.getAllBySupplierIdAsFlow(supplierId).map { ratingModelList ->
            ratingModelList.map { ratingModel ->
                ratingModel.toDomain(getUser(ratingModel.seekerId)!!, supplier!!)
            }
        }
    }

    override suspend fun getAllRatingsBySeekerIdAsFlow(seekerId: Long): Flow<List<Rating>> {
        val seeker = getUser(seekerId)
        return ratingModelDao.getAllBySupplierIdAsFlow(seekerId).map { ratingModelList ->
            ratingModelList.map { ratingModel ->
                ratingModel.toDomain(seeker!!, getUser(ratingModel.supplierId)!!)
            }
        }
    }

}
