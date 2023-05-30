package de.fhe.adoptapal.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
data class AddressModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val houseNumber: String,
    val street: String,
    val city: String,
    val zipCode: String
)

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val name: String,
    val email: String,
    val addressId: Long?,
    val phoneNumber: String?
)

@Entity
data class RatingModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val supplierId: Long,
    val seekerId: Long,
    val rating: RatingEnum
)

@Entity
data class AnimalModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val name: String,
    val birthday: LocalDate,
    val supplierId: Long,
    val animalCategoryId: Long,
    val description: String,
    val colorId: Long,
    val imageFilePath: String?,
    val isMale: Boolean,
    val weight: Float
)

@Entity
data class ColorModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val name: String
)

@Entity
data class AnimalCategoryModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val name: String
)

@Entity
data class RequestModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val seekerId: Long,
    val animalCategoryId: Long,
    val description: String
)

@Entity
data class FavoriteModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val seekerId: Long,
    val animalId: Long
)
