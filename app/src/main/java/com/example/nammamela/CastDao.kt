package com.example.nammamela

import androidx.room.*

@Dao
interface CastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cast: Cast)

    @Query("SELECT * FROM cast_table")
    suspend fun getAll(): List<Cast>

    @Query("DELETE FROM cast_table")
    suspend fun deleteAll()
}