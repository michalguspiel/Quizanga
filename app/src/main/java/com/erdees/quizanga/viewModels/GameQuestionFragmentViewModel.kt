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

class GameQuestionFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val basicRepository: BasicRepository
    private val gameStateRepository: GameStateRepository
    private val playerRepository: PlayerRepository

    init {
        val gameStateDao = AppRoomDatabase.getDatabase(application).gameStateDao()
        val playerDao = AppRoomDatabase.getDatabase(application).playerDao()
        val basicDao = BasicDatabase.getInstance().basicDao
        basicRepository = BasicRepository(basicDao)
        gameStateRepository = GameStateRepository(gameStateDao)
        playerRepository = PlayerRepository(playerDao)
    }

    fun updatePoints(player: Player) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepository.updatePoints(player)
        }
    }

    fun updateGameState(gameState: GameState) {
        viewModelScope.launch(Dispatchers.IO) {
            gameStateRepository.updateGame(gameState)
        }
    }

    fun getPlayersForThisGame(gameId: Long) = playerRepository.getPlayersFromGame(gameId)


    fun getQuestion() = basicRepository.getQuestions()

    fun addMoreQuestions(difficult: LevelOfDifficult) = basicRepository.setQuestionsOrAddIfLiveDataAlreadyExists(difficult)

}