package de.fhe.adoptapal.network.retrofit

import de.fhe.adoptapal.network.retrofit.model.PositionsStackLatLongLookupResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val POSITIONSTACK_API_KEY = "308b13a618f76143a5a342bf6b36039b"

interface Api {

    @GET(
        "forward?access_key=${POSITIONSTACK_API_KEY}" +
                "&limit=1"
    )
    @Headers("Accept: application/json")
    suspend fun getLatLong(
        @Query("query") stringAddress: String,
    )
            : PositionsStackLatLongLookupResponse
}