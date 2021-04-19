package com.erdees.quizanga.gameLogic

import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.*


class QuizangaApplication() {

    private object NoActionScreenCallback : (Screen) -> Unit {
        override fun invoke(screen: Screen) {}
    }

    private var screenCallback: (Screen) -> Unit = NoActionScreenCallback
    lateinit var game: Game
    lateinit var screen: Screen
    val playerList : MutableList<Player> = mutableListOf()

    var sessionQuestionCounter = 0

    fun incrementQuestionCounter() {
        sessionQuestionCounter ++
    }

    fun setUpGame() {
        screen = SetGameScreen(listOf())
        this.screenCallback(screen)
    }


    fun startGame(){
        if(game.numberOfTurns==0) return
        game.numberOfTurnsLeft = game.numberOfTurns
        game.hasStarted = true
        setScreen()
    }

    fun proceedWithQuestion(){
        if(game.numberOfTurnsLeft == 0) return // open screen Game Result to show End Result, scoreboards, whatevs TODO
        else screen = GameQuestionScreen(game.players[game.currentTurnCounter])
    }

    fun open() {
        game = Game()
    }

    fun addPlayer(player : Player){
        playerList.add(player)
    }

    fun savePlayers(playerList : MutableList<Player>) {
        game.players = playerList
        screen = SetGameScreen(playerList as List<Player>)
    }

    fun setScreen() {
        screen = if (!game.hasStarted) WelcomeScreen()
        else GameScoreboardScreen(game)
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




