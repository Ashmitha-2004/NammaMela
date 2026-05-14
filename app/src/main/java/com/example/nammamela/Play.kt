package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play_table")
data class Play(
    @PrimaryKey
    val id: Int = 1,
    val title: String,
    val time: String,
    val duration: String,
    val imageUrl: String
)