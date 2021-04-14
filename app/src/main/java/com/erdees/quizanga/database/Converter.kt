package com.erdees.quizanga.database

import androidx.room.TypeConverter
import com.erdees.quizanga.levelOfDifficult.Easy
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult

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