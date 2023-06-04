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

    @Query("SELECT * FROM AddressModel")
    suspend fun getAll(): List<AddressModel>

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
interface RatingModelDao {

    @Query("SELECT * FROM RatingModel")
    fun getAllAsFlow(): Flow<List<RatingModel>>

    @Query("SELECT * FROM RatingModel")
    suspend fun getAll(): List<RatingModel>

    @Query("SELECT * FROM RatingModel WHERE id = :id")
    suspend fun get(id: Long): RatingModel?


    @Query("SELECT * FROM RatingModel WHERE supplierId = :supplierId")
    suspend fun getBySuppliedId(supplierId: Long): RatingModel?

    @Query("SELECT * FROM RatingModel WHERE seekerId = :seekerId")
    suspend fun getBySeekerId(seekerId: Long): RatingModel?


    @Query("SELECT * FROM RatingModel WHERE rating > :rating")
    suspend fun getByRatingIsAbove(rating: Int): RatingModel?


    @Upsert
    suspend fun upsert(entity: RatingModel): Long

    @Delete
    suspend fun delete(entity: RatingModel)

    @Delete
    suspend fun delete(vararg entities: RatingModel)

    @Query("DELETE FROM RatingModel")
    suspend fun deleteAll()
}


@Dao
interface AnimalModelDao {

    @Query("SELECT * FROM AnimalModel")
    fun getAllAsFlow(): Flow<List<AnimalModel>>

    @Query("SELECT * FROM AnimalModel")
    suspend fun getAll(): List<AnimalModel>

    @Query("SELECT * FROM AnimalModel WHERE id = :id")
    suspend fun get(id: Long): AnimalModel?

    @Query("SELECT * FROM AnimalModel WHERE supplierId = :supplierId")
    suspend fun getByUserId(supplierId: Long): AnimalModel?

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
interface FavoriteModelDao {

    @Query("SELECT * FROM FavoriteModel")
    fun getAllAsFlow(): Flow<List<FavoriteModel>>

    @Query("SELECT * FROM FavoriteModel")
    suspend fun getAll(): List<FavoriteModel>

    @Query("SELECT * FROM FavoriteModel WHERE id = :id")
    suspend fun get(id: Long): FavoriteModel?

    @Query("SELECT * FROM FavoriteModel WHERE seekerId = :seekerId")
    suspend fun getBySeekerId(seekerId: Long): FavoriteModel?

    @Query("SELECT * FROM FavoriteModel WHERE animalId = :animalId")
    suspend fun getByAnimalId(animalId: Long): FavoriteModel?

    @Upsert
    suspend fun upsert(entity: FavoriteModel): Long

    @Delete
    suspend fun delete(entity: FavoriteModel)

    @Query("DELETE FROM FavoriteModel WHERE animalId = :animalId")
    suspend fun deleteByAnimalId(animalId: Long)

    @Delete
    suspend fun delete(vararg entities: FavoriteModel)

    @Query("DELETE FROM FavoriteModel")
    suspend fun deleteAll()
}

@Dao
interface AnimalCategoryModelDao {

    @Query("SELECT * FROM AnimalCategoryModel")
    fun getAllAsFlow(): Flow<List<AnimalCategoryModel>>

    @Query("SELECT * FROM AnimalCategoryModel")
    suspend fun getAll(): List<AnimalCategoryModel>

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

    @Query("SELECT * FROM ColorModel")
    suspend fun getAll(): List<ColorModel>

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

@Dao
interface RequestModelDao {

    @Query("SELECT * FROM RequestModel")
    fun getAllAsFlow(): Flow<List<RequestModel>>

    @Query("SELECT * FROM RequestModel")
    suspend fun getAll(): List<RequestModel>

    @Query("SELECT * FROM RequestModel WHERE id = :id")
    suspend fun get(id: Long): RequestModel?

    @Query("SELECT * FROM RequestModel WHERE animalCategoryId = :animalCategoryId")
    suspend fun getByAnimalCategoryId(animalCategoryId: Long): RequestModel?

    @Query("SELECT * FROM RequestModel WHERE seekerId = :seekerId")
    suspend fun getBySeekerId(seekerId: Long): RequestModel?

    @Upsert
    suspend fun upsert(entity: RequestModel): Long

    @Delete
    suspend fun delete(entity: RequestModel)

    @Delete
    suspend fun delete(vararg entities: RequestModel)

    @Query("DELETE FROM RequestModel")
    suspend fun deleteAll()
}