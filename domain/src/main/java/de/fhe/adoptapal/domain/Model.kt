package de.fhe.adoptapal.domain

import java.time.LocalDate
import java.time.LocalDateTime


enum class AsyncOperationState {
    LOADING,
    SAVING,
    SUCCESS,
    ERROR,
    UNDEFINED;
}

data class AsyncOperation(
    val status: AsyncOperationState,
    val message: String,
    val payload: Any = Unit
) {

    companion object {

        fun success(message: String = "Async Op Successful", payload: Any = Unit): AsyncOperation {
            return AsyncOperation(AsyncOperationState.SUCCESS, message, payload)
        }

        fun saving(message: String = "Async Saving"): AsyncOperation {
            return AsyncOperation(AsyncOperationState.SAVING, message)
        }

        fun error(message: String = "Error on Async Op", payload: Any = Unit): AsyncOperation {
            return AsyncOperation(AsyncOperationState.ERROR, message, payload)
        }

        fun loading(message: String = "Async Loading"): AsyncOperation {
            return AsyncOperation(AsyncOperationState.LOADING, message)
        }

        fun undefined(message: String = "No Async Op / Undefined"): AsyncOperation {
            return AsyncOperation(AsyncOperationState.UNDEFINED, message)
        }
    }
}

data class User(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var name: String,
    var email: String,
    var address: Address?,
    var phoneNumber: String?,
    var useCoarseLocation: Boolean
)

data class Address(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var houseNumber: String,
    var street: String,
    var city: String,
    var zipCode: String,
    var latitude: Double,
    var longitude: Double
)

data class Location(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

data class Animal(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    val name: String,
    val birthday: LocalDate,
    val supplier: User,
    var animalCategory: AnimalCategory,
    val description: String,
    var color: Color,
    val imageFilePath: String?,
    val isMale: Boolean,
    val weight: Float,
    val isFavorite: Boolean
)

data class Color(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var name: String
)

data class AnimalCategory(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var name: String
)

