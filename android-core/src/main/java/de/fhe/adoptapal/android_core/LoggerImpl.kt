package de.fhe.adoptapal.android_core

import de.fhe.adoptapal.domain.Logger
import timber.log.Timber


private const val CALLER_STACK_TRACE_POSITION = 4

/**
 * Logger implementation
 */
class LoggerImpl : Logger {

    companion object {
        init {
//            if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.i("Logger initialized")
//            }
        }
    }

    override fun error(component: String, message: String) {
        Timber.tag(component).e(message)
    }

    override fun info(component: String, message: String) {
        Timber.tag(component).i(message)
    }

    override fun error(message: String) {
        this.error(callingCallName(), message)
    }

    override fun info(message: String) {
        this.info(callingCallName(), message)
    }

    private fun callingCallName(): String {
        val fullClassName =
            Thread.currentThread().stackTrace[CALLER_STACK_TRACE_POSITION]?.className
        return fullClassName?.substring(fullClassName.lastIndexOf('.')) ?: "UnknownCaller"
    }
}