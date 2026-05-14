package com.example.nammamela

import androidx.room.*

@Dao
interface FanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: FanComment)

    @Query("SELECT * FROM fan_comments")
    suspend fun getAll(): List<FanComment>

    @Query("SELECT COUNT(*) FROM fan_comments")
    suspend fun getCount(): Int
}
