package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult

class BasicDao {

    private var lastAddedGameId = 0L
    private val lastAddedGameIdLive = MutableLiveData<Long>()

    private var amountOfPlayers = 0
    private val amountOfPlayersLive = MutableLiveData<Int>()

    private var amountOfGameTurns = 0
    private val amountOfGameTurnsLive = MutableLiveData<Int>()

    private var difficultLevel: LevelOfDifficult? = null
    private val difficultLevelLive = MutableLiveData<LevelOfDifficult?>()

    init {
        lastAddedGameIdLive.value = lastAddedGameId
        amountOfPlayersLive.value = amountOfPlayers
        amountOfGameTurnsLive.value = amountOfGameTurns
        difficultLevelLive.value = difficultLevel
    }

    fun getLastAddedGameId() = lastAddedGameIdLive as LiveData<Long>

    fun setLastAddedGameId(gameId: Long){
        lastAddedGameId = gameId
        lastAddedGameIdLive.postValue(lastAddedGameId)
    }

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