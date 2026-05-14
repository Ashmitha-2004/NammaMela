package com.example.nammamela

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Seat::class, Cast::class, Play::class, FanComment::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun seatDao(): SeatDao
    abstract fun castDao(): CastDao
    abstract fun playDao(): PlayDao
    abstract fun fanDao(): FanDao
}

