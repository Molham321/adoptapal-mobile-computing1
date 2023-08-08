package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.BuildConfig
import de.fhe.adoptapal.android_core.LocalStoreImpl
import de.fhe.adoptapal.android_core.LoggerImpl
import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.RepositoryImpl
import de.fhe.adoptapal.domain.CreateAnimalAsync
import de.fhe.adoptapal.domain.CreateAnimalCategoryAsync
import de.fhe.adoptapal.domain.CreateColorAsync
import de.fhe.adoptapal.domain.DeleteAnimalAsync
import de.fhe.adoptapal.domain.GetAddressAsync
import de.fhe.adoptapal.domain.GetAllAnimalCategories
import de.fhe.adoptapal.domain.GetAllAnimals
import de.fhe.adoptapal.domain.GetAllColors
import de.fhe.adoptapal.domain.GetAllFavoriteAnimalsAsync
import de.fhe.adoptapal.domain.GetAllRatingsBySeekerId
import de.fhe.adoptapal.domain.GetAllRatingsBySupplierId
import de.fhe.adoptapal.domain.GetAllUsers
import de.fhe.adoptapal.domain.GetAnimalAsync
import de.fhe.adoptapal.domain.GetAnimalByRangeAsync
import de.fhe.adoptapal.domain.GetAnimalCategoryAsync
import de.fhe.adoptapal.domain.GetColorAsync
import de.fhe.adoptapal.domain.GetLatLongForAddress
import de.fhe.adoptapal.domain.GetLoggedInUserFromDataStoreAndDatabase
import de.fhe.adoptapal.domain.GetRatingAsync
import de.fhe.adoptapal.domain.GetUserAnimalsAsync
import de.fhe.adoptapal.domain.GetUserAsync
import de.fhe.adoptapal.domain.GetUserByEmailAsync
import de.fhe.adoptapal.domain.GetUsersByRangeAsync
import de.fhe.adoptapal.domain.InsertAddressAsync
import de.fhe.adoptapal.domain.InsertRatingAsync
import de.fhe.adoptapal.domain.InsertUserAsync
import de.fhe.adoptapal.domain.LocalStore
import de.fhe.adoptapal.domain.Logger
import de.fhe.adoptapal.domain.Repository
import de.fhe.adoptapal.domain.SetLoggedInUserInDataStore
import de.fhe.adoptapal.domain.UpdateAnimalAsync
import de.fhe.adoptapal.domain.UpdateUserAsync
import de.fhe.adoptapal.ui.screens.addAnimal.AddAnimalScreenViewModel
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.map.MapScreenViewModel
import de.fhe.adoptapal.ui.screens.profile.ProfileScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel
import de.fhe.adoptapal.ui.screens.settings.SettingsScreenViewModel
import de.fhe.adoptapal.ui.screens.userDetail.UserDetailScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single<Repository> {
        RepositoryImpl(
            AppDatabase.getUserModelDao(get()),
            AppDatabase.getAddressModelDao(get()),
            AppDatabase.getRatingModelDao(get()),
            AppDatabase.getAnimalModelDao(get()),
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

    single<Logger> {
        LoggerImpl()
    }

    single<LocalStore> {
        LocalStoreImpl(get())
    }
}


val networkModule = module {
    single {
        BuildConfig.NET_IMPL_TYPE.implementation
    }
}

val useCaseModule = module {
    // animal
    factory { GetAllAnimals(get()) }
    factory { GetAnimalByRangeAsync(get()) }
    factory { CreateAnimalAsync(get()) }
    factory { GetAnimalAsync(get()) }
    factory { GetUserAnimalsAsync(get()) }
    factory { UpdateAnimalAsync(get()) }
    factory { GetAllFavoriteAnimalsAsync(get()) }
    factory { DeleteAnimalAsync(get()) }

    // color
    factory { GetAllColors(get()) }
    factory { CreateColorAsync(get()) }
    factory { GetColorAsync(get()) }

    // animal category
    factory { GetAllAnimalCategories(get()) }
    factory { CreateAnimalCategoryAsync(get()) }
    factory { GetAnimalCategoryAsync(get()) }

    // user
    factory { GetAllUsers(get()) }
    factory { GetUsersByRangeAsync(get()) }
    factory { GetUserAsync(get()) }
    factory { GetUserByEmailAsync(get()) }
    factory { InsertUserAsync(get()) }
    factory { UpdateUserAsync(get()) }

    // Address
    factory { GetAddressAsync(get()) }
    factory { InsertAddressAsync(get()) }

    // Rating
    factory { GetRatingAsync(get()) }
    factory { InsertRatingAsync(get()) }
    factory { GetAllRatingsBySeekerId(get()) }
    factory { GetAllRatingsBySupplierId(get()) }

    // LocalStore
    factory { GetLoggedInUserFromDataStoreAndDatabase(get(), get()) }
    factory { SetLoggedInUserInDataStore(get()) }

    // Network
    factory { GetLatLongForAddress(get()) }
}

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get(), get(), get(), get(), get(), get()) }

    viewModel { DetailScreenViewModel(get(), get(), get(), get()) }
    viewModel { UserDetailScreenViewModel(get(), get(), get(), get()) }

    viewModel { LoginScreenViewModel(get(), get(), get()) }
    viewModel { MapScreenViewModel(get(), get(), get()) }

    viewModel { RegisterScreenViewModel(get(), get()) }
    viewModel { AddAnimalScreenViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ProfileScreenViewModel(get(), get(), get()) }
    viewModel { SettingsScreenViewModel(get(), get(), get(), get()) }
}
