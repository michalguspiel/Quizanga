package com.erdees.quizanga.gameLogic

import android.util.Log
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.*


class QuizangaApplication {

    private object NoActionScreenCallback : (Screen) -> Unit {
        override fun invoke(screen: Screen) {}
    }

    private var screenCallback: (Screen) -> Unit = NoActionScreenCallback
    lateinit var game: Game
    lateinit var screen: Screen


    var hasProblemOccurred = false
    var sessionQuestionCounter = 0

    fun incrementQuestionCounter() {
        sessionQuestionCounter++
        this.screenCallback(screen)
    }

    fun setUpGame() {
        screen = SetGameScreen()
        this.screenCallback(screen)
    }


    fun startGame() {
        if (game.numberOfTurns == 0) return
        game.numberOfTurnsLeft = game.numberOfTurns
        game.hasStarted = true
        setScreen()
    }

    fun proceedWithQuestion() {
        if (game.numberOfTurnsLeft == 0) {
            game.hasEnded = true
            screen = ResultScreen()
        } else screen = GameQuestionScreen()

        this.screenCallback(screen)
    }

    fun restartGame() {
        game = Game()
        setScreen()
        this.screenCallback(screen)
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
        //updateScreen(screen)

    }


    fun withScreenCallback(callback: (Screen) -> Unit) {
        Log.i("TEST", "with screen callback casted!")
        this.screenCallback = callback
        updateScreen(screen)
    }

    private fun updateScreen(screen: Screen) {
        this.screen = screen
        screenCallback(this.screen)

    }

}




