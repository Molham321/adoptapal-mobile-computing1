package de.fhe.adoptapal.di.modules

import de.fhe.adoptapal.data.AppDatabase
import de.fhe.adoptapal.data.UserModelDao
import org.koin.dsl.module


val databaseModule = module {
    single<Repository> {
        RepositoryImpl(
            AppDatabase.getUserModelDao(get())
        )
    }
}

class RepositoryImpl(userEntityDao: UserModelDao) : Repository{

}

interface Repository {

}