package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val androidCoreModule = module {
    single {
        NavigationManager()
    }
}

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get()) }
    viewModel { DetailScreenViewModel(get()) }
}