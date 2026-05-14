package com.example.nammamela

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeatDao {

    @Query("SELECT * FROM Seat")
    suspend fun getAll(): List<Seat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seat: Seat)

    @Query("DELETE FROM Seat WHERE bookedByUser = 1")
    suspend fun deleteUserBookings()
}