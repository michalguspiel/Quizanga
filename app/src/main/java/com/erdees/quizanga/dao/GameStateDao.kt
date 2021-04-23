package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.quizanga.models.GameState

@Dao
interface GameStateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun startGame(game : GameState) : Long

    @Query("SELECT * FROM gamestate WHERE numberOfTurnsLeft > 0 ")
    fun getActiveGame(): LiveData<GameState>

    @Update
    suspend fun updateGame(game:GameState)

    @Delete
    suspend fun deleteGameState(game: GameState)


}