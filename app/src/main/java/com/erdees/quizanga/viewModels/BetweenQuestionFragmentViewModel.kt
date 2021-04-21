package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.repository.PlayerRepository

class BetweenQuestionFragmentViewModel(application: Application):AndroidViewModel(application) {

    private val playerRepository: PlayerRepository
    init {
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        playerRepository = PlayerRepository(playerDao)
    }

    fun getPlayersForThisGame(gameId: Long) = playerRepository.getPlayersFromGame(gameId)

}