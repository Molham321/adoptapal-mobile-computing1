package de.fhe.adoptapal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface UserModelDao {

    @Query("SELECT * FROM UserModel")
    fun getAllAsFlow(): Flow<List<UserModel>>

    @Query("SELECT * FROM UserModel")
    suspend fun getAll(): List<UserModel>

    @Query("SELECT * FROM UserModel WHERE id = :id")
    suspend fun get(id: Long): UserModel?

    @Query("SELECT * FROM UserModel WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserModel?

    @Upsert
    suspend fun upsert(entity: UserModel): Long

    @Delete
    suspend fun delete(entity: UserModel)

    @Delete
    suspend fun delete(vararg entities: UserModel)

    @Query("DELETE FROM UserModel")
    suspend fun deleteAll()
}

@Dao
interface AddressModelDao {

    @Query("SELECT * FROM AddressModel")
    fun getAllAsFlow(): Flow<List<AddressModel>>

    @Query("SELECT * FROM AddressModel WHERE id = :id")
    suspend fun get(id: Long): AddressModel?

    @Upsert
    suspend fun upsert(entity: AddressModel): Long

    @Delete
    suspend fun delete(entity: AddressModel)

    @Delete
    suspend fun delete(vararg entities: AddressModel)

    @Query("DELETE FROM AddressModel")
    suspend fun deleteAll()
}

@Dao
interface AnimalModelDao {

    @Query("SELECT * FROM AnimalModel")
    fun getAllAsFlow(): Flow<List<AnimalModel>>

    @Query("SELECT * FROM AnimalModel WHERE supplierId = :id")
    fun getUserAnimalsAsFlow(id: Long): Flow<List<AnimalModel>>

    @Query("SELECT * FROM AnimalModel WHERE isFavorite = 1")
    fun getAllFavoriteAnimalsAsFlow(): Flow<List<AnimalModel>>

    @Query("SELECT * FROM AnimalModel WHERE id = :id")
    suspend fun get(id: Long): AnimalModel?

    @Query("SELECT * FROM AnimalModel WHERE supplierId = :supplierId")
    suspend fun getBySupplierId(supplierId: Long): AnimalModel?

    @Query("SELECT * FROM AnimalModel WHERE colorId = :colorId")
    suspend fun getByColorId(colorId: Long): AnimalModel?

    @Query("SELECT * FROM AnimalModel WHERE animalCategoryId = :animalCategoryId")
    suspend fun getByAnimalCategoryId(animalCategoryId: Long): AnimalModel?

    @Upsert
    suspend fun upsert(entity: AnimalModel): Long

    @Delete
    suspend fun delete(entity: AnimalModel)

    @Delete
    suspend fun delete(vararg entities: AnimalModel)

    @Query("DELETE FROM AnimalModel")
    suspend fun deleteAll()
}


@Dao
interface AnimalCategoryModelDao {

    @Query("SELECT * FROM AnimalCategoryModel")
    fun getAllAsFlow(): Flow<List<AnimalCategoryModel>>

    @Query("SELECT * FROM AnimalCategoryModel WHERE id = :id")
    suspend fun get(id: Long): AnimalCategoryModel?

    @Upsert
    suspend fun upsert(entity: AnimalCategoryModel): Long

    @Delete
    suspend fun delete(entity: AnimalCategoryModel)

    @Delete
    suspend fun delete(vararg entities: AnimalCategoryModel)

    @Query("DELETE FROM AnimalCategoryModel")
    suspend fun deleteAll()
}

@Dao
interface ColorModelDao {

    @Query("SELECT * FROM ColorModel")
    fun getAllAsFlow(): Flow<List<ColorModel>>

    @Query("SELECT * FROM ColorModel WHERE id = :id")
    suspend fun get(id: Long): ColorModel?

    @Upsert
    suspend fun upsert(entity: ColorModel): Long

    @Delete
    suspend fun delete(entity: ColorModel)

    @Delete
    suspend fun delete(vararg entities: ColorModel)

    @Query("DELETE FROM ColorModel")
    suspend fun deleteAll()
}
