package de.fhe.adoptapal.network.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.NetworkController
import de.fhe.adoptapal.network.core.LoggerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitNetworkController() : NetworkController {

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


    /*
        Internal Helper
     */

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


}