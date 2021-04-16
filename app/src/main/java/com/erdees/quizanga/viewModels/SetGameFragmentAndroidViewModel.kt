package com.erdees.quizanga.viewModels

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SetGameFragmentAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val gameStateRepository : GameStateRepository
    private val playerRepository : PlayerRepository

    init {
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        val gameStateDao = AppRoomDatabase.getDatabase(application).gameStateDao()
        gameStateRepository = GameStateRepository(gameStateDao)
        playerRepository = PlayerRepository(playerDao)

    }

    fun savePlayersIntoGame(playerList: List<Player>){
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.savePlayersIntoGame(playerList)
        }
    }
   fun startGame(gameState : GameState):Long = runBlocking {
             gameStateRepository.startGame(gameState)
       }


}