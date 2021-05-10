package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository

class SetGameFragmentViewModel(application: Application) : AndroidViewModel(application) {

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

    fun setAmountOfPlayers(number: Int) {
        basicRepository.setAmountOfPlayers(number)
    }

    fun getAmountOfPlayers() = basicRepository.getAmountOfPlayers()

    fun setAmountOfGameTurns(number : Int){
        basicRepository.setAmountOfGameTurns(number)
    }
    fun getAmountOfGameTurns()  = basicRepository.getAmountOfGameTurns()


    fun setLevelOfDifficulty(level: LevelOfDifficult){
        basicRepository.setLevelOfDifficulty(level)
    }

    fun getDifficultLevel() = basicRepository.getDifficultLevel()

    fun addMoreQuestions(difficult: LevelOfDifficult) = basicRepository.setQuestionsOrAddIfLiveDataAlreadyExists(difficult)

    fun getQuestions() = basicRepository.getQuestions()

    fun getActiveGameState() = stateRepository.getActiveGame()

    fun getPlayersFromThisGameState(gameStateId : Long) = playersRepository.getPlayersFromGame(gameStateId)

}