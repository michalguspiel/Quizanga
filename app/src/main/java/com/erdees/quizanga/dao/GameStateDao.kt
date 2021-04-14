package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erdees.quizanga.Game
import com.erdees.quizanga.models.GameState

@Dao
interface GameStateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun startGame(game : GameState)

    @Query("SELECT * FROM gamestate WHERE numberOfTurnsLeft > 0")
    fun getActiveGame(): LiveData<GameState>

}