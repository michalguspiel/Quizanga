package com.erdees.quizanga.repository

import android.util.Log
import com.erdees.quizanga.dao.BasicDao
import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.models.GameState

class GameStateRepository(private val stateDao: GameStateDao) {

     suspend fun startGame(game: GameState): Long  {
        val gameID = stateDao.startGame(game)
        return gameID
    }

    suspend fun updateGame(game:GameState){
        stateDao.updateGame(game)
    }

    fun getActiveGame() = stateDao.getActiveGame()

    companion object {
        @Volatile
        private var instance: GameStateRepository? = null
        fun getInstance(gameStateDao: GameStateDao) =
            instance ?: synchronized(this) {
                instance
                    ?: GameStateRepository(gameStateDao).also { instance = it }
            }
    }
}