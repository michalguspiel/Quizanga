package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.repository.StateAndPlayersRepository

class GamesHistoryViewModel(application: Application): AndroidViewModel(application) {

    private val stateAndPlayersRepository : StateAndPlayersRepository

    init {
        val stateAndPlayersDao = AppRoomDatabase.getDatabase(application).stateAndPlayersDao()
        stateAndPlayersRepository = StateAndPlayersRepository(stateAndPlayersDao)
    }

    fun getGamesHistory() = stateAndPlayersRepository.getStateAndPlayersHistory()

}