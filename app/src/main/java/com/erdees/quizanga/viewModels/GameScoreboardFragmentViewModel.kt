package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.repository.PlayerRepository

class GameScoreboardFragmentViewModel(application: Application):AndroidViewModel(application) {

    private val playersRepository : PlayerRepository
    init {
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        playersRepository = PlayerRepository(playerDao)
    }

    fun getPlayersForThisGame(gameId: Long) = playersRepository.getPlayersFromGame(gameId)
}