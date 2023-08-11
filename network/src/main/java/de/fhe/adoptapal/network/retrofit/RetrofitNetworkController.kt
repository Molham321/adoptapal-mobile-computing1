package de.fhe.adoptapal.network.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.domain.NetworkController
import de.fhe.adoptapal.network.core.LoggerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Retrofit Networl implementation
 */
class RetrofitNetworkController : NetworkController {

    /**
     * Address to latLong conversion
     */
    override fun getLatLongFromAddress(address: Address): Flow<Address> = flow {
        val stringAddress =
            "${address.street} ${address.houseNumber}, ${address.city} ${address.zipCode}"
        val response = api.getLatLong(
            stringAddress = stringAddress
        )
        address.latitude = response.data[0].latitude!!
        address.longitude = response.data[0].longitude!!

        emit(address)
    }

    /**
     * Search location String to LatLong conversion
     * uses FHE Address as Fallback
     */
    override fun getLatLongByLocationString(locationString: String): Flow<Location> = flow {
        try {
            val response = api.getLatLong(locationString)
            val newLocation =  Location(
                latitude = response.data[0].latitude!!,
                longitude = response.data[0].longitude!!
            )

            emit(newLocation)
        } catch (ex: HttpException) {
            LoggerFactory.getLogger().info("Retrofit", ex.message())
            emit(fheFallbackLatLong)
        } catch (ex: Exception) {
            ex.message?.let { LoggerFactory.getLogger().info("Retrofit", it) }
            emit(fheFallbackLatLong)
        }
    }

    private var api: Api

    init {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val httpClientBuilder = OkHttpClient.Builder()

        // Configure Logging
        val loggingInterceptor =
            HttpLoggingInterceptor { LoggerFactory.getLogger().info("Retrofit", it) }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClientBuilder.addInterceptor(loggingInterceptor)

        // Configure Header Interceptor
        httpClientBuilder.addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("User-Agent", "Android Clean Architecture Template (v0.0.1) / Retrofit")
                .build()
            it.proceed(request)
        }

        // Build API Client
        api = Retrofit.Builder()
            .baseUrl("http://api.positionstack.com/v1/")
            .client(httpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
            .create(Api::class.java)
    }

    companion object {
        private val fheFallbackLatLong = Location(50.98464,11.042537)
    }

}
