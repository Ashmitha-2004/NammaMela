package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seat_table")
data class Seat(

    @PrimaryKey
    val seatId: String,   // A1, A2 etc

    val showKey: String,  // ⭐ NEW IMPORTANT FIELD

    val isBooked: Boolean = false,

    val bookedByUser: Boolean = false
)