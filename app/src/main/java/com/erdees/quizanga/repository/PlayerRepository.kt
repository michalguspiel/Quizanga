package com.erdees.quizanga.repository

import android.util.Log
import com.erdees.quizanga.dao.PlayerDao
import com.erdees.quizanga.fragments.GameQuestionFragment
import com.erdees.quizanga.models.Player

class PlayerRepository(private val playerDao: PlayerDao) {

    suspend fun savePlayerIntoGame(player: Player) = playerDao.savePlayerIntoGame(player)

    suspend fun savePlayersIntoGame(playerList: List<Player>) {
        playerList.forEach { playerDao.savePlayerIntoGame(it) }
    }

    fun getPlayersFromGame(gameId: Long) = playerDao.getPlayersFromGame(gameId)

    suspend fun deletePlayers(playerList: List<Player>) = playerDao.deletePlayers(playerList)

    suspend fun updatePoints(player: Player){
    Log.i("GameQuestionFragment" ," points updated ${player.points}")
        playerDao.updatePlayer(player)

    }

}