package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BasicDao {

    private var amountOfPlayers = 0
    private val amountOfPlayersLive = MutableLiveData<Int>()

    private var amountOfGameTurns = 0
    private val amountOfGameTurnsLive = MutableLiveData<Int>()

    init {
        amountOfPlayersLive.value = amountOfPlayers
        amountOfGameTurnsLive.value = amountOfGameTurns
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


}