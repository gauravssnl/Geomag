package com.sanmer.geomag.data.database

import androidx.room.*

@Dao
interface RecordDao {
    @Query("SELECT * FROM records")
    fun getAll(): List<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: RecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<RecordEntity>)

    @Delete
    suspend fun delete(value: RecordEntity)

    @Delete
    suspend fun delete(list: List<RecordEntity>)

    @Query("DELETE FROM records")
    suspend fun deleteAll()
}