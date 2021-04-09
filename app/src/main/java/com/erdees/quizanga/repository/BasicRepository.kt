package com.erdees.quizanga.repository

import com.erdees.quizanga.dao.BasicDao

class BasicRepository(val dao: BasicDao) {

    fun setAmountOfPlayers(number: Int) = dao.setAmountOfPlayers(number)
    fun getAmountOfPlayers() = dao.getAmountOfPlayers()

}