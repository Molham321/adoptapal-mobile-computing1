package de.fhe.adoptapal.network.retrofit.model

/**
 * Positionstack Api Return value list
 */
data class PositionsStackLatLongLookupResponse(
    val data: List<Result>
)

/**
 * Positionstack Api Return value
 */
data class Result(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var label: String? = null,
    var name: String? = null,
    var type: String? = null,
    var number: String? = null,
    var street: String? = null,
    var postalCode: String? = null,
    var confidence: Double? = null,
    var region: String? = null,
    var regionCode: String? = null,
    var administrativeArea: String? = null,
    var neighbourhood: String? = null,
    var country: String? = null,
    var countryCode: String? = null,
    var mapUrl: String? = null
)