package com.erdees.quizanga.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.models.Questions
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository
import com.erdees.quizanga.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameQuestionFragmentViewModel(application: Application): AndroidViewModel(application) {

val basicRepository : BasicRepository
val gameStateRepository : GameStateRepository
val playerRepository : PlayerRepository

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

    fun updateGameState(gameState : GameState ){
        viewModelScope.launch(Dispatchers.IO) {
            gameStateRepository.updateGame(gameState)
        }
    }

    fun getQuestion() = basicRepository.getQuestions()

    fun addMoreQuestions() = basicRepository.setQuestionsOrAddIfLivedataAlreadyExists()

}