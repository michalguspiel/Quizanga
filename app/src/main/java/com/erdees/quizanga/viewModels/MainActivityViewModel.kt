package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    private val stateRepository : GameStateRepository
    private val playersRepository : PlayerRepository
    private val basicRepository : BasicRepository

    init {
        val basicDao = BasicDatabase.getInstance().basicDao
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        val stateDao = AppRoomDatabase.getDatabase(application).gameStateDao()
        stateRepository = GameStateRepository(stateDao)
        playersRepository = PlayerRepository(playerDao)
        basicRepository = BasicRepository(basicDao)
    }

    fun getDataAboutProblems() = basicRepository.getDataAboutProblemsWithConnectionOrQuestions()

    fun addQuestions(difficult: LevelOfDifficult) = basicRepository.setQuestionsOrAddIfLiveDataAlreadyExists(difficult)
    fun getQuestions() = basicRepository.getQuestions()

    fun getActiveGameState() = stateRepository.getActiveGame()

    fun getPlayersFromThisGameState(gameId: Long) = playersRepository.getPlayersFromGame(gameId)

    fun deleteGameState(gameState: GameState){
        viewModelScope.launch(Dispatchers.IO) {
            stateRepository.deleteGameState(gameState)
        }
    }

    fun deletePlayers(playerList : List<Player>){
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.deletePlayers(playerList)
        }
    }

}