package de.fhe.adoptapal

import android.app.Application
import de.fhe.adoptapal.di.modules.androidCoreModule
import de.fhe.adoptapal.di.modules.databaseModule
import de.fhe.adoptapal.di.modules.networkModule
import de.fhe.adoptapal.di.modules.useCaseModule
import de.fhe.adoptapal.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * The `Application` subclass that serves as the entry point for initializing the app's components using Koin.
 */
class Bootstrap : Application() {

    /**
     * Called when the application is starting. Initializes the app's components using Koin and runs database tests.
     */
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin dependency injection
        startKoin {
            // TODO: maybe add a logger here
            androidContext(this@Bootstrap)

            // Load app modules using Koin
            modules(databaseModule)
            modules(androidCoreModule)
            modules(viewModelModule)
            modules(useCaseModule)
            modules(networkModule)
        }

        // Test database initialization with initial data
        DBInitialData().run()
    }
}


