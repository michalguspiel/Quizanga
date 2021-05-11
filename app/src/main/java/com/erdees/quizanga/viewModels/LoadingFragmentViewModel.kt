package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository
import kotlinx.coroutines.runBlocking

class LoadingFragmentViewModel(application: Application) : AndroidViewModel(application) {

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
    fun addQuestions(difficult: LevelOfDifficult) = basicRepository.setQuestionsOrAddIfLiveDataAlreadyExists(difficult)

    fun getDataAboutProblem() = basicRepository.getDataAboutProblemsWithConnectionOrQuestions()

    fun getActiveGameState() = stateRepository.getActiveGame()

    fun getPlayersFromThisGameState(gameId: Long) = runBlocking {
        playersRepository.getPlayersFromGame(gameId)
    }

    fun getQuestions() = basicRepository.getQuestions()

}