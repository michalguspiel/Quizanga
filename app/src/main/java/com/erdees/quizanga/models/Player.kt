package com.erdees.quizanga.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)   val playerId: Long,
    var gameId: Long = 0L,
    val name: String,
    var points: Int
){
    @Ignore
    fun pointsAfterCorrectAnswer(pointsToAdd: Int) = points + pointsToAdd

    @Ignore
    fun pointsAfterWrongAnswer(pointsToRemove : Int) = points - pointsToRemove

}