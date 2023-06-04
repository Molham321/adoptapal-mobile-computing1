package de.fhe.adoptapal.data

import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Location
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

    override fun getAllAnimals(): Flow<List<Animal>> {
        return animalModelDao.getAllAsFlow().map { animalEntityList ->
            animalEntityList.map { animalEntity ->
                val animal = getAnimalWithContent(animalEntity)
                animal
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

        val animal = animalEntity.toDomain(
            supplier = supplier!!,
            animalCategory = animalCategory!!,
            color = color!!
        )

        return animal
    }

    override suspend fun insertAnimal(animal: Animal): Long {
        return animalModelDao.upsert(animal.fromDomain());
    }

    override suspend fun deleteAnimal(animal: Animal) {
        animalModelDao.delete(animal.fromDomain())
        favoriteModelDao.deleteByAnimalId(animal.id)
    }

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

    override fun getAllUsers(): Flow<List<User>> {
        return userModelDao.getAllAsFlow().map { userEntityList ->
            userEntityList.map { userEntity ->
                userEntity.toDomain(userEntity.addressId?.let { getAddress(it) })
            }
        }
    }

    override fun getUsersByRange(location: Location, distance: Int): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: Long): User? {
        val userModel = userModelDao.get(userId)
        val addressModel = userModel?.addressId?.let { addressModelDao.get(it) }

        return userModelDao.get(userId)?.toDomain(addressModel?.toDomain())
    }

    override suspend fun insertUser(user: User): Long {
        return userModelDao.upsert(user.fromDomain())
    }

    override suspend fun getAddress(addressId: Long): Address? {
        return addressModelDao.get(addressId)?.toDomain()
    }

    override suspend fun insertAddress(address: Address): Long {
        return addressModelDao.upsert(address.fromDomain())
    }
}
