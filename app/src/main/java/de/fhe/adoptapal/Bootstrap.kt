package de.fhe.adoptapal

import android.app.Application
import de.fhe.adoptapal.di.modules.androidCoreModule
import de.fhe.adoptapal.di.modules.databaseModule
import de.fhe.adoptapal.di.modules.useCaseModule
import de.fhe.adoptapal.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Bootstrap : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // TODO: maybe add a logger here
            androidContext(this@Bootstrap)

            // modules
            modules(databaseModule)
            modules(androidCoreModule)
            modules(viewModelModule)
            modules(useCaseModule)
        }

        // test Database
        DBInitialData().run()
    }
}


