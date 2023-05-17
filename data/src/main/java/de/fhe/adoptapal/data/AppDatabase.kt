package de.fhe.adoptapal.data

import android.content.Context
import android.location.Address
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [
    UserModel::class,
    AddressModel::class,
    RatingModel::class,
    AnimalModel::class,
    ColorModel::class,
    AnimalCategoryModel::class,
    RequestModel::class,
    FavoriteModel::class
], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userModelDao(): UserModelDao
//    abstract fun addressEntityDao(): AddressEntityDao

    companion object {
        var db: AppDatabase? = null

        private fun getDatabase(app: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(app, AppDatabase::class.java, "app-db")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return db as AppDatabase
        }

        fun getUserModelDao(app: Context): UserModelDao {
            return getDatabase(app).userModelDao()
        }

    }
}

