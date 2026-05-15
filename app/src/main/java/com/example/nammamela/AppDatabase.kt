package com.example.nammamela

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Seat::class,
        Cast::class,
        Play::class,
        FanComment::class
    ],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun seatDao(): SeatDao
    abstract fun castDao(): CastDao
    abstract fun playDao(): PlayDao
    abstract fun fanDao(): FanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nammamela_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}