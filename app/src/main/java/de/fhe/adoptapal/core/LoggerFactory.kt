package de.fhe.adoptapal.core

import de.fhe.adoptapal.android_core.LoggerImpl
import de.fhe.adoptapal.domain.Logger

object LoggerFactory {
    private val logger by lazy { LoggerImpl() }

    fun getLogger(): Logger = logger
}
