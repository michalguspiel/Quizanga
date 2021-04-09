package com.erdees.quizanga

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class QuizangaApplicationUnitTests {

    val quizangaApplication = QuizangaApplication()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `Given the game isn't started when I open app Game hasStarted is false `(){
        quizangaApplication.open()
        assertEquals(quizangaApplication.game.hasStarted,false)
    }

    @Test
    fun `Given the game is not started when I open app player array is empty`(){
        quizangaApplication.open()
        assert(quizangaApplication.game.players.isEmpty())
    }

    @Test
    fun `Given I see Start Game screen when I press Start Game Then I see GameSettings screen `(){
        quizangaApplication.open()
        quizangaApplication.startGame()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is SetGameScreen)
        }
    }

    @Test
    fun `Given I open app and the game isnt started i see welcome screen`(){
        quizangaApplication.open()
        quizangaApplication.setInitialScreen()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true,screen is WelcomeScreen)
        }
    }
    @Test
    fun `Given I reopen app when Game was on I see GameScreen`(){
        quizangaApplication.open()
        quizangaApplication.game.hasStarted = true
        quizangaApplication.setInitialScreen()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is GameScreen)
        }
    }

    @Test
    fun `When calling function changing amount of players  game playersAmount variable actually changes`() {
        quizangaApplication.open()
        quizangaApplication.setAmountOfPlayers(5)
        assertEquals(5,quizangaApplication.game.playersAmount)
    }

    @Test
    fun `When`(){}


}

