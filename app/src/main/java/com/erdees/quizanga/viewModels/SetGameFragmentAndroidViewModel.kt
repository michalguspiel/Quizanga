package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetGameFragmentAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val gameStateRepository : GameStateRepository
    val playerRepository : PlayerRepository

    init {
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        val basicDao = BasicDatabase.getInstance().basicDao
        val gameStateDao = AppRoomDatabase.getDatabase(application).gameStateDao()
        gameStateRepository = GameStateRepository(gameStateDao,basicDao)
        playerRepository = PlayerRepository(playerDao)

    }

    fun savePlayersIntoGame(playerList: List<Player>){
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.savePlayersIntoGame(playerList)
        }
    }

    fun startGame(gameState : GameState) {
        viewModelScope.launch(Dispatchers.IO){
            gameStateRepository.startGame(gameState)
        }

    }

}