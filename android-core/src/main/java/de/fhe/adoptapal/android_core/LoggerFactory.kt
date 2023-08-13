package de.fhe.adoptapal.android_core

import de.fhe.adoptapal.domain.Logger

object LoggerFactory {
    private val logger by lazy { LoggerImpl() }

    fun getLogger(): Logger = logger
}
