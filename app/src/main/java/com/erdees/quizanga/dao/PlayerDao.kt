package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erdees.quizanga.models.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayerIntoGame(player: Player)

    @Query("SELECT * FROM PLAYER WHERE gameId = :gameId")
    fun getPlayersFromGame(gameId: Long): LiveData<List<Player>>

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayers(playerList: List<Player>)

}