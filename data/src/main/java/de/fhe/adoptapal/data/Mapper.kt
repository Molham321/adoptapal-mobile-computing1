package de.fhe.adoptapal.data

import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color
import de.fhe.adoptapal.domain.User

fun AnimalModel.toDomain(supplier: User, animalCategory: AnimalCategory, color: Color) = Animal(
    id = id,
    createdTimestamp = createdTimestamp,
    lastChangeTimestamp = lastChangeTimestamp,
    name = name,
    supplier = supplier,
    color = color,
    animalCategory = animalCategory,
    birthday = birthday,
    description = description,
    imageFilePath = imageFilePath,
    isMale = isMale,
    weight = weight
)

fun Animal.fromDomain() =
    AnimalModel(
        id = id,
        createdTimestamp = createdTimestamp,
        name = name,
        birthday = birthday,
        supplierId = supplier.id,
        animalCategoryId = animalCategory.id,
        description = description,
        colorId = color.id,
        imageFilePath = imageFilePath,
        isMale = isMale,
        weight = weight
    )


fun ColorModel.toDomain() = Color(
    id = id,
    createdTimestamp = createdTimestamp,
    lastChangeTimestamp = lastChangeTimestamp,
    name = name
)

fun Color.fromDomain() = ColorModel(
    id = id,
    createdTimestamp = createdTimestamp,
    name = name
)

fun AnimalCategoryModel.toDomain() = AnimalCategory(
    id = id,
    createdTimestamp = createdTimestamp,
    lastChangeTimestamp = lastChangeTimestamp,
    name = name
)

fun AnimalCategory.fromDomain() = AnimalCategoryModel(
    id = id,
    createdTimestamp = createdTimestamp,
    name = name
)

fun AddressModel.toDomain() = Address(
    id = id,
    createdTimestamp = createdTimestamp,
    lastChangeTimestamp = lastChangeTimestamp,
    houseNumber = houseNumber,
    street = street,
    city = city,
    zipCode = zipCode
)

fun Address.fromDomain() = AddressModel(
    id = id,
    createdTimestamp = createdTimestamp,
    houseNumber = houseNumber,
    street = street,
    city = city,
    zipCode = zipCode
)

fun UserModel.toDomain(address: Address?) = User(
    id = id,
    createdTimestamp = createdTimestamp,
    lastChangeTimestamp = lastChangeTimestamp,
    name = name,
    email = email,
    address = address,
    phoneNumber = phoneNumber
)

fun User.fromDomain() = UserModel(
    id = id,
    createdTimestamp = createdTimestamp,
    name = name,
    email = email,
    addressId = address?.id,
    phoneNumber = phoneNumber
)