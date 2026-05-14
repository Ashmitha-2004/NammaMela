package com.example.nammamela

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(play: Play)

    @Query("SELECT * FROM play_table WHERE id = 1")
    suspend fun getPlay(): Play?
}
