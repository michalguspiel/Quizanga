package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val stateRepository : GameStateRepository
    private val playersRepository : PlayerRepository
    init {
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        val stateDao = AppRoomDatabase.getDatabase(application).gameStateDao()
        stateRepository = GameStateRepository(stateDao)
        playersRepository = PlayerRepository(playerDao)
    }

    fun getActiveGameState() = stateRepository.getActiveGame()

    fun getPlayersFromThisGameState(gameId: Long) = playersRepository.getPlayersFromGame(gameId)
}