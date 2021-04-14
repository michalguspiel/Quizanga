package com.erdees.quizanga.levelOfDifficult

import androidx.room.Entity
import androidx.room.TypeConverters
import com.erdees.quizanga.database.Converter


interface LevelOfDifficult {
    var name: String
    fun pointsRemovedPerWrongAnswer(): Int
    fun pointsAddedForCorrectAnswer(): Int

}


