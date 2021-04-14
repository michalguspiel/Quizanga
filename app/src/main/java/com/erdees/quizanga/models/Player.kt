package com.erdees.quizanga.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)   val playerId: Long,
    val name: String,
    var points: Int
)