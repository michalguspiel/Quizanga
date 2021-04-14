package com.erdees.quizanga.models

import androidx.room.*
import com.erdees.quizanga.database.Converter
import com.erdees.quizanga.levelOfDifficult.Easy
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult

@Entity
class GameState(
    @PrimaryKey(autoGenerate = true)val gameId : Long,
   // @Embedded val playerList: List<Player> = listOf(),
    val numberOfTurnsLeft: Int,
    @TypeConverters(Converter::class)
    @ColumnInfo(name = "difficultyLevel")
    var difficultyLevel: LevelOfDifficult,
    val roundCounter : Int
) {

}