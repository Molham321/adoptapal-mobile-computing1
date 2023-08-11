package de.fhe.adoptapal.data

import androidx.room.Entity
import androidx.room.Index
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
    val zipCode: String,
    val latitude: Double,
    val longitude: Double
)

@Entity(indices = [Index(value = ["email"], unique = true)])
data class UserModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val name: String,
    val email: String,
    val addressId: Long?,
    val phoneNumber: String?,
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
    val weight: Float,
    val isFavorite: Boolean
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
