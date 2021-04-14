package com.erdees.quizanga

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.GameScreen
import com.erdees.quizanga.screens.Screen
import com.erdees.quizanga.screens.SetGameScreen
import com.erdees.quizanga.screens.WelcomeScreen
import com.erdees.quizanga.viewModels.QuizangaApplicationViewModel


class QuizangaApplication(val viewModelStoreOwner: ViewModelStoreOwner) {

    private object NoActionScreenCallback : (Screen) -> Unit {
        override fun invoke(screen: Screen) {}
    }

    private var screenCallback: (Screen) -> Unit = NoActionScreenCallback
    lateinit var game: Game
    lateinit var screen: Screen
    lateinit var viewModel : QuizangaApplicationViewModel
    val playerList : MutableList<Player> = mutableListOf()

    fun setUpGame() {
        screen = SetGameScreen(listOf())
        this.screenCallback(screen)
    }


    fun startGame(){
        if(game.numberOfTurns==0) return
        game.numberOfTurnsLeft = game.numberOfTurns
        game.hasStarted = true
        viewModel = ViewModelProvider(this.viewModelStoreOwner).get(QuizangaApplicationViewModel::class.java)
        //TODO CREATE GAME STATE IN ROOM DATABASE

        setScreen()
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
        else GameScreen(game)
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




