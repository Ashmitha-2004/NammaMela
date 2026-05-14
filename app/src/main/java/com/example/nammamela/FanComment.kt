package com.example.nammamela

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fan_comments")
data class FanComment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val comment: String
)
