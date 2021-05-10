package com.erdees.quizanga.repository

import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.models.GameState

class GameStateRepository(private val stateDao: GameStateDao) {

     suspend fun startGame(game: GameState): Long  {
         return stateDao.startGame(game)
    }

    suspend fun updateGame(game:GameState){
        stateDao.updateGame(game)
    }

    suspend fun deleteGameState(game: GameState){
        stateDao.deleteGameState(game)
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