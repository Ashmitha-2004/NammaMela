package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Seat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val seatNumber: String,
    val isBooked: Boolean
)
