package com.erdees.quizanga.database.converter

import androidx.room.TypeConverter
import com.erdees.quizanga.gameLogic.levelOfDifficult.Easy
import com.erdees.quizanga.gameLogic.levelOfDifficult.Hard
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.gameLogic.levelOfDifficult.Medium
import java.lang.Exception
import java.util.logging.Level

class Converter {
    @TypeConverter
    fun convertToLevel(string: String): LevelOfDifficult{
        return when (string){
            "Easy" -> Easy
            "Medium" -> Medium
            "Hard" -> Hard
            else -> throw Exception("Error!")
        }
    }
    @TypeConverter
        fun levelToString(level: LevelOfDifficult): String{
        return level.name
        }
}