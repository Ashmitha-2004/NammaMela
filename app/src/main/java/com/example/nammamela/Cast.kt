package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast_table")
data class Cast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val role: String,
    val imageUrl: String
)
