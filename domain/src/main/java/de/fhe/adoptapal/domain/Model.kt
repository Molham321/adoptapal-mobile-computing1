package de.fhe.adoptapal.domain

import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


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
    var phoneNumber: String?
) {
    constructor(
        name: String,
        email: String,
        phoneNumber: String?
    ): this(0, LocalDateTime.now(), LocalDateTime.now(), name, email, null, phoneNumber )
}

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
) {

    fun calculateDistanceTo(lat2: Double, long2: Double): Double {
        return calculateDistance(this.latitude, this.longitude, lat2, long2)
    }

    fun isWithinRangeOf(lat2: Double, long2: Double, distanceInKm: Double): Boolean {
        return isLocationWithinRange(
            this.latitude,
            this.longitude,
            lat2,
            long2,
            distanceInKm.toDouble()
        )
    }

    companion object {
        private const val EARTH_RADIUS = 6371.0 // Earth's radius in kilometers

        /**
         * calculate if the distance between two LatLong points is in a range in KM
         */
        fun isLocationWithinRange(
            lat1: Double,
            lon1: Double,
            lat2: Double,
            lon2: Double,
            rangeInKm: Double
        ): Boolean {
            val distance = calculateDistance(lat1, lon1, lat2, lon2)
            return distance <= rangeInKm
        }

        /**
         * calculate the distance between two LatLong points
         */
        fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            // Convert latitude and longitude to radians
            val lat1Rad = Math.toRadians(lat1)
            val lon1Rad = Math.toRadians(lon1)
            val lat2Rad = Math.toRadians(lat2)
            val lon2Rad = Math.toRadians(lon2)

            // Calculate the differences between the coordinates
            val dLat = lat2Rad - lat1Rad
            val dLon = lon2Rad - lon1Rad

            // Apply the Haversine formula
            val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            val distance = EARTH_RADIUS * c

            return distance
        }
    }
}

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


data class Rating(
    var id: Long = 0,
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),
    var lastChangeTimestamp: LocalDateTime = LocalDateTime.now(),
    var seeker: User,
    var supplier: User,
    var rating: RatingEnum,
    var comment: String
)


enum class RatingEnum {
    ONE_STAR, TWO_STARS, THREE_STARS, FOUR_STARS, FIVE_STARS
}
