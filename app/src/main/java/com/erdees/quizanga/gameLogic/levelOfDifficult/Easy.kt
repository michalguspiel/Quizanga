package com.erdees.quizanga.gameLogic.levelOfDifficult

object Easy : LevelOfDifficult {
    override var name: String = "Easy"
    override fun pointsRemovedPerWrongAnswer(): Int = 0
    override fun pointsAddedForCorrectAnswer(): Int = 100

}