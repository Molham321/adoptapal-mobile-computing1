package de.fhe.adoptapal.data

import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import java.time.LocalDate

fun AnimalModel.toDomain() = Animal(
    id = id,
    name = name,
    birthday = birthday,
    supplierId = supplierId,
    description = description,
    imageFilePath = imageFilePath,
    isMale = isMale,
    weight = weight
)

fun Animal.fromDomain() =
        AnimalModel(
        id = id,
        name = name,
        birthday = birthday,
        supplierId = supplierId,
        animalCategoryId = animalCategory!!.id,
        description = description,
        colorId = color!!.id,
        imageFilePath = imageFilePath,
        isMale = isMale,
        weight = weight
    )


fun ColorModel.toDomain() = Color(
    id = id,
    name = name
)

fun Color.fromDomain() = ColorModel(
    id = id,
    name = name
)

fun AnimalCategoryModel.toDomain() = AnimalCategory(
    id = id,
    name = name
)

fun AnimalCategory.fromDomain() = AnimalCategoryModel(
    id = id,
    name = name
)