package de.fhe.adoptapal.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime


@Entity
abstract class BaseModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false
)

@Entity
data class AddressModel(
    val houseNumber: String,
    val street: String,
    val city: String,
    val zipCode: String
) : BaseModel()

@Entity
data class UserModel(
    val name: String,
    val email: String,
    val addressId: Long,
    val phoneNumber: String
) : BaseModel()

@Entity
data class RatingModel(
    val supplierId: Long,
    val seekerId: Long,
    val rating: RatingEnum
) : BaseModel()

@Entity
data class AnimalModel(
    val name: String,
    val birthday: LocalDate,
    val supplierId: Long,
    val animalCategoryId: Long,
    val description: String,
    val colorId: Long,
    val imageFilePath: String,
    val isMale: Boolean,
    val weight: Float
) : BaseModel()

@Entity
data class ColorModel(
    val name: String
) : BaseModel()

@Entity
data class AnimalCategoryModel(
    val name: String
) : BaseModel()

@Entity
data class RequestModel(
    val seekerId: Long,
    val animalCategoryId: Long,
    val description: String
) : BaseModel()

@Entity
data class FavoriteModel(
    val seekerId: Long,
    val animalId: Long
) : BaseModel()
