package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.repository.BasicRepository

class SetGameFragmentViewModel() : ViewModel() {

    val basicRepository : BasicRepository

    init {
        val basicDao = BasicDatabase.getInstance().basicDao
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


}