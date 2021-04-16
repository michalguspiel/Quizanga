package com.erdees.quizanga.gameLogic

import com.erdees.quizanga.levelOfDifficult.Easy
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.Player

class Game{
    var hasEnded = false
    var hasStarted  = false
    var playersAmount = 0
    var difficultLevel: LevelOfDifficult = Easy
    var players : List<Player> = listOf()
    var numberOfTurns = 0
    var numberOfTurnsLeft = numberOfTurns
    var gameId = 0L

    lateinit var gameWinner: Player

    private fun turnLength():Int = playersAmount

    var currentTurnCounter = 1

    fun playerWithTurn(): Player {
        return players[currentTurnCounter-1]
    }

    fun setAmountOfPlayers(number: Int) {
        if (hasStarted)return
        playersAmount = number
    }


    fun setAmountOfGameTurns(number: Int) {
        if(hasStarted) return
        numberOfTurns = number
    }

    private fun highestScoredPlayer(): Player {
        var highestSoFar  = players.first()
        for(eachPlayer in players.drop(1)){
            if(eachPlayer.points > highestSoFar.points) highestSoFar = eachPlayer
        }
        return highestSoFar
    }

    private fun iterateTurn() {
        currentTurnCounter += 1
        if(currentTurnCounter > turnLength()){
            currentTurnCounter = 1
            numberOfTurnsLeft -= 1
        }

        if(numberOfTurnsLeft == 0){
            hasEnded = true
            gameWinner = highestScoredPlayer()
        }


    }

    fun wrongAnswer(player: Player){
        player.points -= difficultLevel.pointsRemovedPerWrongAnswer()
        iterateTurn()
    }

    fun correctAnswer(player: Player){
        player.points += difficultLevel.pointsAddedForCorrectAnswer()
        iterateTurn()
    }



}