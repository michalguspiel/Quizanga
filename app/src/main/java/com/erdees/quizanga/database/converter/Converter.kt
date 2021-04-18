package com.erdees.quizanga.database.converter

import androidx.room.TypeConverter
import com.erdees.quizanga.gameLogic.levelOfDifficult.Easy
import com.erdees.quizanga.gameLogic.levelOfDifficult.Hard
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult

class Converter {
    @TypeConverter
    fun convertToLevel(string: String): LevelOfDifficult{
        return if(string == "Easy") Easy
        else Hard
    }
    @TypeConverter
        fun levelToString(level: LevelOfDifficult): String{
        return level.name
        }
}