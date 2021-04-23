package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.erdees.quizanga.models.StateAndPlayers

@Dao
interface StateAndPlayersDao {

    @Query("SELECT * FROM GAMESTATE WHERE gameId = :gameId")
    fun getStateAndPlayersOfThisGame(gameId:Long) : LiveData<StateAndPlayers>

    @Query("SELECT * FROM GAMESTATE WHERE numberOfTurnsLeft = 0 ORDER BY gameId DESC")
    fun getStateAndPlayersHistory() : LiveData<List<StateAndPlayers>>
}