package com.example.nammamela

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeatDao {

    @Query("SELECT * FROM seat_table WHERE showKey = :showKey")
    suspend fun getSeats(showKey: String): List<Seat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seat: Seat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(seats: List<Seat>)

    @Query("DELETE FROM seat_table WHERE showKey = :showKey")
    suspend fun clearShowSeats(showKey: String)
}