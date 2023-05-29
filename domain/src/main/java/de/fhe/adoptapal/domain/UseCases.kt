package de.fhe.adoptapal.domain;

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetAnimals(private val repository: Repository) {
    operator fun invoke() : Flow<AsyncOperation> = flow {
        emit (AsyncOperation.loading("Start loading animals..."))
        repository.getAllAnimals().collect() {
            emit(AsyncOperation.success("Users loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

 class CreateAnimalAsync(private val repository: Repository) {
    operator fun invoke(newAnimal: Animal) : Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating animal..."))

        val animalId = repository.insertAnimal(newAnimal)

        emit(AsyncOperation.success("Animal saved with ID $animalId"))
    }
}


class GetAllColors(private val repository: Repository) {
    operator fun invoke() : Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading colors..."))
        repository.getAllColors().collect() {
            emit(AsyncOperation.success("Colors loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class CreateColorAsync(private val repository: Repository) {
    operator fun invoke(newColor: Color) : Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating color..."))

        val colorId = repository.insertColor(newColor)

        emit(AsyncOperation.success("Color saved with ID $colorId"))
    }
}

class GetAllAnimalCategories(private val repository: Repository) {
    operator fun invoke() :Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start loading animalCategories..."))
        repository.getAllAnimalCategories().collect() {
            emit(AsyncOperation.success("AnimalCategories loaded", it))
            emit(AsyncOperation.undefined())
        }
    }
}

class CreateAnimalCategoryAsync(private val repository: Repository) {
    operator fun invoke(newAnimalCategory: AnimalCategory) : Flow<AsyncOperation> = flow {
        emit(AsyncOperation.loading("Start creating animalCategory..."))

        val animalCategoryId = repository.insertAnimalCategory(newAnimalCategory)

        emit(AsyncOperation.success("AnimalCategory saved with ID $animalCategoryId"))
    }
}