package com.erdees.quizanga.gameLogic

import android.util.Log
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.*


class QuizangaApplication {

    lateinit var game: Game
    lateinit var screen: Screen


    var hasProblemOccurred = false
    var sessionQuestionCounter = 0

    fun incrementQuestionCounter() {
        sessionQuestionCounter++
    }

    fun setUpGame() {
        screen = SetGameScreen()
    }


    fun startGame() {
        if (game.numberOfTurns == 0) return
        game.numberOfTurnsLeft = game.numberOfTurns
        game.hasStarted = true
        setScreen()
    }


    fun restartGame() {
        game = Game()
        screen = SetGameScreen()
    }

    fun open() {
        game = Game()
    }


    fun savePlayers(playerList: MutableList<Player>) {
        game.players = playerList
        screen = SetGameScreen()
    }

    fun setScreen() {
        screen = when {
            hasProblemOccurred -> LoadingScreen()
            !game.hasStarted -> WelcomeScreen()
            game.currentTurnCounter != 0 && game.players.isEmpty() -> LoadingScreen()
            game.currentTurnCounter != 0 -> GameQuestionScreen()
            game.hasEnded -> ResultScreen()
            else -> GameScoreboardScreen(game)
        }
    }



}




