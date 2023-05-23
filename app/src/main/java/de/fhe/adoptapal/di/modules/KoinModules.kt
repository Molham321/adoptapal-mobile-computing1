package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.ui.screens.core.NavigationManager
import org.koin.dsl.module


val androidCoreModule = module {
    single {
        NavigationManager()
    }
}