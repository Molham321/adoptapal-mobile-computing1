package de.fhe.adoptapal

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Bootstrap : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // TODO: maybe add a logger here
            androidContext(this@Bootstrap)

            // modules
            // modules(androidCoreModule)
        }
    }
}
