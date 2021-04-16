package com.erdees.quizanga.models

import androidx.room.*
import com.erdees.quizanga.database.converter.Converter
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult

@Entity
class GameState(
    @PrimaryKey(autoGenerate = true)val gameId : Long,
    var numberOfTurnsLeft: Int,
    @TypeConverters(Converter::class)
    @ColumnInfo(name = "difficultyLevel")
    var difficultyLevel: LevelOfDifficult,
    var roundCounter : Int
) {

}