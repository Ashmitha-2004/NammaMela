package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Seat(
    @PrimaryKey
    val seatNumber: String = "",

    val isBooked: Boolean = false,

    val bookedByUser: Boolean = false
)