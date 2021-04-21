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
    val playerList: MutableList<Player> = mutableListOf()

    var sessionQuestionCounter = 0

    fun incrementQuestionCounter() {
        sessionQuestionCounter++
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
        } else screen = GameQuestionScreen(game.players[game.currentTurnCounter])
    }

    fun restartGame() {
        game = Game()
    }

    fun open() {
        game = Game()
    }

    fun addPlayer(player: Player) {
        playerList.add(player)
    }

    fun savePlayers(playerList: MutableList<Player>) {
        game.players = playerList
        screen = SetGameScreen()
    }

    fun setScreen() {
        screen = when {
            !game.hasStarted -> WelcomeScreen()
            game.currentTurnCounter != 0 && game.players.isEmpty() -> LoadingScreen()
            game.currentTurnCounter != 0 -> BetweenQuestionScreen()
            game.hasEnded -> ResultScreen()
            else -> GameScoreboardScreen(game)
        }
    }


    fun withScreenCallback(callback: (Screen) -> Unit) {
        this.screenCallback = callback
        updateScreen(screen)
    }

    fun updateScreen(screen: Screen) {
        this.screen = screen
        screenCallback(this.screen)

    }

}




