package com.erdees.quizanga

import com.erdees.quizanga.models.Player

class Game{
    var hasStarted  = false
    var playersAmount = 0
    var players : Array<Player> = arrayOf()
    var numberOfTurns = 0
    var numberOfTurnsLeft = numberOfTurns

}