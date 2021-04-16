package com.erdees.quizanga

import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.GameScoreboardScreen
import com.erdees.quizanga.screens.SetGameScreen
import com.erdees.quizanga.screens.WelcomeScreen
import org.junit.Test

import org.junit.Assert.*

class QuizangaApplicationUnitTests {

    private val quizangaApplication = QuizangaApplication()

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
        quizangaApplication.setUpGame()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is SetGameScreen)
        }
    }

    @Test
    fun `Given I open app and the game isnt started i see welcome screen`(){
        quizangaApplication.open()
        quizangaApplication.setScreen()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true,screen is WelcomeScreen)
        }
    }
    @Test
    fun `Given I reopen app when Game was on I see GameScreen`(){
        quizangaApplication.open()
        quizangaApplication.game.hasStarted = true
        quizangaApplication.setScreen()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is GameScoreboardScreen)
        }
    }

    @Test
    fun `When calling function changing amount of players  game playersAmount variable actually changes`() {
        quizangaApplication.open()
        quizangaApplication.game.setAmountOfPlayers(5)
        assertEquals(5,quizangaApplication.game.playersAmount)
    }

    @Test
    fun `Given I have to make game with 4 players, And I add 4 players to the list, there's 4 players in SetUpGameScreen`(){
        quizangaApplication.open()
        quizangaApplication.game.setAmountOfPlayers(4)
        quizangaApplication.setUpGame()
        quizangaApplication.addPlayer(
            Player(0,name = "Michael",points = 0))
        quizangaApplication.addPlayer(Player(0,name = "Lori", points = 0))
        quizangaApplication.addPlayer(Player(0,name = "Mark",points = 0))
        quizangaApplication.addPlayer(Player(0,name = "Kevin",points = 0))
        quizangaApplication.savePlayers(quizangaApplication.playerList)
            quizangaApplication.withScreenCallback { screen ->
                assertEquals(true, screen is SetGameScreen)
                assertEquals((screen as SetGameScreen).listOfPlayers.size,4)
            }
        }

    private val michal = Player(0,name = "Michal",points = 0)
    private val moona = Player(0,name="Moona",points = 0)
    private val random = Player(0,name="RandomPerson",points = 0)

    private fun setUpTestGame(){
        quizangaApplication.open()
        quizangaApplication.game.setAmountOfPlayers(3)
        quizangaApplication.setUpGame()
        quizangaApplication.addPlayer(michal)
        quizangaApplication.addPlayer(moona)
        quizangaApplication.addPlayer(random)
        quizangaApplication.savePlayers(quizangaApplication.playerList)
        quizangaApplication.game.setAmountOfGameTurns(10)

    }

    @Test
    fun `Given I have all data about game to start, when I start game Game STARTS and screen changes too` (){
        setUpTestGame()
        quizangaApplication.startGame()
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is GameScoreboardScreen)
        }
    }

    @Test
    fun `Given The game has started and I try to change it's settings nothing changes`(){
        setUpTestGame()
        quizangaApplication.startGame()
        quizangaApplication.game.setAmountOfGameTurns(6)
        quizangaApplication.game.setAmountOfPlayers(10)
        quizangaApplication.withScreenCallback { screen ->
            assertEquals(true, screen is GameScoreboardScreen)
        }
        assertEquals(quizangaApplication.game.numberOfTurns,10)
        assertEquals(quizangaApplication.game.playersAmount,3)
    }

    @Test
    fun `Given the player answered correctly his points are 100`(){
        setUpTestGame()
        quizangaApplication.startGame()
        quizangaApplication.game.correctAnswer(michal)
        val michalIndex = quizangaApplication.game.players.indexOf(michal)
        assertEquals(quizangaApplication.game.players[michalIndex].points,100)
    }

    @Test
    fun `Given the player answered wrong and level of difficult is easy so his points are 0 `(){
        setUpTestGame()
        quizangaApplication.startGame()
        quizangaApplication.game.wrongAnswer(michal)
        val michalIndex = quizangaApplication.game.players.indexOf(michal)
        assertEquals(quizangaApplication.game.players[michalIndex].points,0)
    }

    @Test
    fun `Given the player answered wrong and level of difficult is hard so his points are -100 `(){
        setUpTestGame()
        quizangaApplication.game.difficultLevel = Hard
        quizangaApplication.startGame()
        quizangaApplication.game.wrongAnswer(michal)
        val michalIndex = quizangaApplication.game.players.indexOf(michal)
        assertEquals(quizangaApplication.game.players[michalIndex].points,-50)
    }

    @Test
    fun `Given the game just started turns left are the same number as NumberOfTurns`(){
        setUpTestGame()
        quizangaApplication.startGame()
        assertEquals(quizangaApplication.game.numberOfTurnsLeft,quizangaApplication.game.numberOfTurns)
    }

    @Test
    fun `Given the game just started every player has 0 points`(){
        setUpTestGame()
        quizangaApplication.startGame()
        assertEquals(0,quizangaApplication.game.players.map { it.points }.sum())
    }

    @Test
    fun `Given everybody has answered question once, turn is on first player and numbers of turns left are initial numbers of turn -1`(){
        setUpTestGame()
        quizangaApplication.startGame()
        val game = quizangaApplication.game
        game.correctAnswer(game.playerWithTurn())
        game.correctAnswer(game.playerWithTurn())
        game.wrongAnswer(game.playerWithTurn())
        assertEquals(game.numberOfTurns -1 , game.numberOfTurnsLeft)
        assertEquals(game.currentTurnCounter,game.players.indexOf(michal)+1)
    }

    @Test
    fun `Given there is 3 players and everybody answered correctly once, sum of points equals 300` (){
        setUpTestGame()
        quizangaApplication.startGame()
        val game = quizangaApplication.game
        game.correctAnswer(game.playerWithTurn())
        game.correctAnswer(game.playerWithTurn())
        game.wrongAnswer(game.playerWithTurn())
        assertEquals(game.numberOfTurns -1 , game.numberOfTurnsLeft)
        assertEquals(game.currentTurnCounter,game.players.indexOf(michal)+1)
        game.wrongAnswer(game.playerWithTurn())
        assertEquals(game.playerWithTurn(), moona)
        game.wrongAnswer(game.playerWithTurn())
        assertEquals(game.playerWithTurn(),random)
        game.correctAnswer(game.playerWithTurn())
        assertEquals(game.numberOfTurns -2 , game.numberOfTurnsLeft)
        assertEquals(game.playerWithTurn(),michal)
        assertEquals(300,game.players.map { it.points }.sum())
    }

    @Test
    fun `Given numbers of turn left is 0 and the currentTimeCounter is higher than turnLength game Stops and the winner is player with highest amount of points`(){
        setUpTestGame()
        val game = quizangaApplication.game
        game.setAmountOfGameTurns(3)
        quizangaApplication.startGame()

        game.players.forEach { game.correctAnswer(it) }
        game.players.forEach { game.correctAnswer(it) }
        game.correctAnswer(game.playerWithTurn())
        game.wrongAnswer(game.playerWithTurn())
        game.wrongAnswer(game.playerWithTurn())

        assert(game.hasEnded)
        assertEquals(true, game.gameWinner == michal)

    }

    }







