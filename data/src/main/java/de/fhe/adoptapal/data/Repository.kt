package de.fhe.adoptapal.data

import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val userModelDao: UserModelDao,
    private val addressModelDao: AddressModelDao,
    private val ratingModelDao: RatingModelDao,
    private val animalModelDao: AnimalModelDao,
    private val favoriteModelDao: FavoriteModelDao,
    private val animalCategoryModelDao: AnimalCategoryModelDao,
    private val colorModelDao: ColorModelDao,
    private val requestModelDao: RequestModelDao
) : Repository {

    /**
     *
     */
    override fun getAllAnimals(): Flow<List<Animal>> {
        return animalModelDao.getAllAsFlow().map { animalEntityList ->
            animalEntityList.map { animalEntity ->

                // get Data for Animal
                val animalCategory = animalCategoryModelDao.get(animalEntity.animalCategoryId)
                    ?.toDomain()
                val color = colorModelDao.get(animalEntity.colorId)?.toDomain()
                val supplierEntity = userModelDao.get(animalEntity.supplierId)
                val address = supplierEntity?.addressId?.let { addressModelDao.get(it)?.toDomain() }
                val supplier = supplierEntity?.toDomain(address)

                val animal = animalEntity.toDomain(
                    supplier = supplier!!,
                    animalCategory = animalCategory!!,
                    color = color!!
                )

                animal
            }
        }
    }

    override suspend fun insertAnimal(animal: Animal): Long {
        val animalEntity = animal.fromDomain()

        val animalId = animalModelDao.upsert(animalEntity)

        return animalId;
    }

    override fun getAllColors(): Flow<List<Color>> {
        return colorModelDao.getAllAsFlow().map { colorEntityList ->
            colorEntityList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertColor(color: Color): Long {
        val colorEntity = color.fromDomain()

        val colorId = colorModelDao.upsert(colorEntity)

        return colorId
    }

    override suspend fun getColor(colorId: Long): Color? {
        return colorModelDao.get(colorId)?.toDomain()
    }

    override fun getAllAnimalCategories(): Flow<List<AnimalCategory>> {
        return animalCategoryModelDao.getAllAsFlow().map { animalCategoryEntityList ->
            animalCategoryEntityList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun insertAnimalCategory(animalCategory: AnimalCategory): Long {
        val animalCategoryEntity = animalCategory.fromDomain()

        val animalCategoryId = animalCategoryModelDao.upsert(animalCategoryEntity)

        return animalCategoryId
    }

    override suspend fun getAnimalCategory(animalCategoryId: Long): AnimalCategory? {
        return animalCategoryModelDao.get(animalCategoryId)?.toDomain()
    }

    override suspend fun getUser(userId: Long): User? {
        val userModel = userModelDao.get(userId)
        val addressModel = userModel?.addressId?.let { addressModelDao.get(it) }

        return userModelDao.get(userId)?.toDomain(addressModel?.toDomain())
    }

    override suspend fun insertUser(user: User): Long {
        val userEntity = user.fromDomain()

        val userId = userModelDao.upsert(userEntity)

        return userId
    }

    override suspend fun getAddress(addressId: Long): Address? {
        return addressModelDao.get(addressId)?.toDomain()
    }

    override suspend fun insertAddress(address: Address): Long {
        return addressModelDao.upsert(address.fromDomain())
    }


}
