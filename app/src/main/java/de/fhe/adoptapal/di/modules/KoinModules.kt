package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.RepositoryImpl
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel
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

val useCaseModule = module {
    factory { GetAllAnimals(get()) }
    factory { GetAnimalAsync(get()) }
    factory { InsertUserAsync(get()) }
}

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { DetailScreenViewModel(get(), get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { RegisterScreenViewModel(get(), get()) }
}
