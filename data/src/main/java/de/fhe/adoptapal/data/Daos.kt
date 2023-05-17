package de.fhe.adoptapal.data

import android.location.Address
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface UserModelDao {

    @Query("SELECT * FROM UserModel")
    fun getAllAsFlow(): Flow<List<UserModel>>

    @Query("SELECT * FROM UserModel")
    suspend fun getAll(): List<UserModel>

    @Query("SELECT * FROM UserModel WHERE id = :id")
    suspend fun get(id: Long): UserModel?

    @Insert
    suspend fun insert(entity: UserModel): Long

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

    @Insert
    suspend fun insert(entity: AddressModel): Long

    @Delete
    suspend fun delete(entity: AddressModel)

    @Delete
    suspend fun delete(vararg entities: AddressModel)

    @Query("DELETE FROM AddressModel")
    suspend fun deleteAll()
}
