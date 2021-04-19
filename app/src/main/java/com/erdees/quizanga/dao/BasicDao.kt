package com.erdees.quizanga.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.Questions

class BasicDao {

    private var questions : Questions? = null
    private val questionsLive = MutableLiveData<Questions?>()

    private var amountOfPlayers = 0
    private val amountOfPlayersLive = MutableLiveData<Int>()

    private var amountOfGameTurns = 0
    private val amountOfGameTurnsLive = MutableLiveData<Int>()

    private var difficultLevel: LevelOfDifficult? = null
    private val difficultLevelLive = MutableLiveData<LevelOfDifficult?>()

    init {
        amountOfPlayersLive.value = amountOfPlayers
        amountOfGameTurnsLive.value = amountOfGameTurns
        difficultLevelLive.value = difficultLevel
        questionsLive.value = questions
    }

    fun setQuestions(questionsToSet: Questions){
        questions = questionsToSet
        questionsLive.value = questions
    }

    fun addQuestions(questionsToAdd: Questions){
        Log.i("TEST", "ADD questions casted")
        questions = questionsToAdd
        questionsLive.value!!.results += questions!!.results
    }

    fun getQuestions() = questionsLive as LiveData<Questions>

    fun setAmountOfGameTurns(number: Int){
        amountOfGameTurns = number
        amountOfGameTurnsLive.value = amountOfGameTurns
    }

    fun getAmountOfGameTurns() = amountOfGameTurnsLive as LiveData<Int>

    fun setAmountOfPlayers(number: Int ){
        amountOfPlayers = number
        amountOfPlayersLive.value = amountOfPlayers
    }

    fun getAmountOfPlayers() = amountOfPlayersLive as LiveData<Int>


    fun setLevelOfDifficulty(levelOfDifficult: LevelOfDifficult){
        difficultLevel = levelOfDifficult
        difficultLevelLive.value = difficultLevel
    }
    fun getDifficultLevel() = difficultLevelLive as LiveData<LevelOfDifficult>

}