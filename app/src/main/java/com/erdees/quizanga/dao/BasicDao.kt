package com.erdees.quizanga.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BasicDao {

    private var amountOfPlayers = 0
    private val amountOfPlayersLive = MutableLiveData<Int>()

    init {
        amountOfPlayersLive.value = amountOfPlayers
    }

    fun setAmountOfPlayers(number: Int ){
        amountOfPlayers = number
        amountOfPlayersLive.value = amountOfPlayers
    }

    fun getAmountOfPlayers() = amountOfPlayersLive as LiveData<Int>


}