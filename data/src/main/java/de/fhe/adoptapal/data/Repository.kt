package de.fhe.adoptapal.data

import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val animalModelDao: AnimalModelDao,
    private val colorModelDao: ColorModelDao,
    private val animalCategoryModelDao: AnimalCategoryModelDao
) :Repository {

    /**
     * 
     */
    override fun getAllAnimals(): Flow<List<Animal>> {
        return animalModelDao.getAllAsFlow().map { animalEntityList ->
            animalEntityList.map {animalEntity ->
                val animal = animalEntity.toDomain()
                animal.animalCategory = animalCategoryModelDao.get(animalEntity.animalCategoryId)?.toDomain()
                animal.color = colorModelDao.get(animalEntity.colorId)?.toDomain()
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

}
