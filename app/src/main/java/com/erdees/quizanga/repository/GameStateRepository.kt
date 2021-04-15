package com.erdees.quizanga.repository

import com.erdees.quizanga.dao.BasicDao
import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.models.GameState

class GameStateRepository(private val stateDao: GameStateDao,private val basicDao: BasicDao) {

    suspend fun startGame(game: GameState): Long  {
        val gameID = stateDao.startGame(game)
        setLastAddedGameId(gameID)
        return gameID
    }

    suspend fun updateGame(game:GameState){
        stateDao.updateGame(game)
    }

    fun getActiveGame() = stateDao.getActiveGame()

    private fun setLastAddedGameId(gameId: Long) = basicDao.setLastAddedGameId(gameId)

    fun lastAddedGameId() = basicDao.getLastAddedGameId() //This is here for ROOM TESTS

    companion object {
        @Volatile
        private var instance: GameStateRepository? = null
        fun getInstance(gameStateDao: GameStateDao,basicDao: BasicDao) =
            instance ?: synchronized(this) {
                instance
                    ?: GameStateRepository(gameStateDao,basicDao).also { instance = it }
            }
    }
}