package com.erdees.quizanga.repository

import com.erdees.quizanga.dao.StateAndPlayersDao

class StateAndPlayersRepository(private val dao: StateAndPlayersDao) {

    fun getStateAndPlayersOfThisGame(gameId: Long) = dao.getStateAndPlayersOfThisGame(gameId)

    fun getStateAndPlayersHistory() = dao.getStateAndPlayersHistory()

}