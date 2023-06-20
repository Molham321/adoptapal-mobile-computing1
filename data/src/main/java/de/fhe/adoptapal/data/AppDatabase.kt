package de.fhe.adoptapal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        UserModel::class,
        AddressModel::class,
        RatingModel::class,
        AnimalModel::class,
        ColorModel::class,
        AnimalCategoryModel::class,
        RequestModel::class,
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userModelDao(): UserModelDao
    abstract fun addressModelDao(): AddressModelDao
    abstract fun ratingModelDao(): RatingModelDao
    abstract fun animalModelDao(): AnimalModelDao
    abstract fun animalCategoryDao(): AnimalCategoryModelDao
    abstract fun colorModelDao(): ColorModelDao
    abstract fun requestModelDao(): RequestModelDao

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

        // create dao get methods
        fun getUserModelDao(app: Context): UserModelDao {
            return getDatabase(app).userModelDao()
        }

        fun getAddressModelDao(app: Context): AddressModelDao {
            return getDatabase(app).addressModelDao()
        }

        fun getRatingModelDao(app: Context): RatingModelDao {
            return getDatabase(app).ratingModelDao()
        }

        fun getAnimalModelDao(app: Context): AnimalModelDao {
            return getDatabase(app).animalModelDao()
        }


        fun getAnimalCategoryModelDao(app: Context): AnimalCategoryModelDao {
            return getDatabase(app).animalCategoryDao()
        }

        fun getColorModelDao(app: Context): ColorModelDao {
            return getDatabase(app).colorModelDao()
        }

        fun getRequestModelDao(app: Context): RequestModelDao {
            return getDatabase(app).requestModelDao()
        }
    }
}

