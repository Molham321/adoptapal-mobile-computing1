package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.RepositoryImpl
import de.fhe.adoptapal.data.UserModelDao
import de.fhe.adoptapal.domain.Repository
import org.koin.dsl.module
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single<Repository> {
        RepositoryImpl(
            AppDatabase.getUserModelDao(get()),
            AppDatabase.getAddressModelDao(get()),
            AppDatabase.getRatingModelDao(get()),
            AppDatabase.getAnimalModelDao(get()),
            AppDatabase.getFavoriteModelDao(get()),
            AppDatabase.getAnimalCategoryModelDao(get()),
            AppDatabase.getColorModelDao(get()),
            AppDatabase.getRequestModelDao(get())
        )
    }
}

val androidCoreModule = module {
    single {
        NavigationManager()
    }
}

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get()) }
    viewModel { DetailScreenViewModel(get()) }
}
