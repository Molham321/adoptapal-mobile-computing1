package de.fhe.adoptapal.network.core

import de.fhe.adoptapal.domain.NetworkController
import de.fhe.adoptapal.network.retrofit.RetrofitNetworkController

/**
 * Base Network implementation definition
 */
sealed class NetworkImplType {
    val implementation: NetworkController by lazy { initImpl() }

    open fun initImpl(): NetworkController {
        return RetrofitNetworkController()
    }
}

/**
 * Retrofit Network implementation type
 */
object RETROFIT : NetworkImplType() {
    override fun initImpl(): NetworkController {
        return RetrofitNetworkController()
    }
}