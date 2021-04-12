package com.erdees.quizanga

import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.GameScreen
import com.erdees.quizanga.screens.Screen
import com.erdees.quizanga.screens.SetGameScreen
import com.erdees.quizanga.screens.WelcomeScreen


class QuizangaApplication {

    private object NoActionScreenCallback : (Screen) -> Unit {
        override fun invoke(screen: Screen) {}
    }

    private var screenCallback: (Screen) -> Unit = NoActionScreenCallback
    lateinit var game: Game
    lateinit var screen: Screen
    val playerList : MutableList<Player> = mutableListOf()

    fun setUpGame() {
        screen = SetGameScreen(listOf())
        this.screenCallback(screen)
    }


    fun startGame(){
        if(game.numberOfTurns==0) return
        game.numberOfTurnsLeft = game.numberOfTurns
        game.hasStarted = true
        setInitialScreen()
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

    fun setInitialScreen() {
        screen = if (!game.hasStarted) WelcomeScreen()
        else GameScreen(game)
    }


    fun withScreenCallback(callback: (Screen) -> Unit) {
        this.screenCallback = callback
        updateScreen(screen)
    }

    private fun updateScreen(screen: Screen) {
        this.screen = screen
        screenCallback(this.screen)

    }

}




