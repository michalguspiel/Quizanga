package com.erdees.quizanga.repository

import com.erdees.quizanga.dao.BasicDao
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult

class BasicRepository(val dao: BasicDao) {

    fun setAmountOfPlayers(number: Int) = dao.setAmountOfPlayers(number)
    fun getAmountOfPlayers() = dao.getAmountOfPlayers()

    fun setAmountOfGameTurns(number: Int) = dao.setAmountOfGameTurns(number)
    fun getAmountOfGameTurns() = dao.getAmountOfGameTurns()

    fun setLevelOfDifficulty(level: LevelOfDifficult) = dao.setLevelOfDifficulty(level)
    fun getDifficultLevel() = dao.getDifficultLevel()

}