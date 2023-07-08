package de.fhe.adoptapal.network.core

import de.fhe.adoptapal.domain.NetworkController
import de.fhe.adoptapal.network.retrofit.RetrofitNetworkController

sealed class NetworkImplType() {
    val implementation: NetworkController by lazy { initImpl() }

    open fun initImpl(): NetworkController {
        return RetrofitNetworkController()
    }
}

object DEFAULT : NetworkImplType()

object RETROFIT : NetworkImplType() {
    override fun initImpl(): NetworkController {
        return RetrofitNetworkController()
    }
}