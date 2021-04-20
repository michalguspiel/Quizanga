package com.erdees.quizanga.screens

import com.erdees.quizanga.models.Player

class GameQuestionScreen(val player: Player): Screen {


    override fun toString(): String {
       return super.toString() + "$player.name"

    }
}