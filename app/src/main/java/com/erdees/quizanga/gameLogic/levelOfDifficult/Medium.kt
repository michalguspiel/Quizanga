package com.erdees.quizanga.gameLogic.levelOfDifficult

object Medium: LevelOfDifficult {
    override var name: String = "Medium"
    override fun pointsAddedForCorrectAnswer(): Int = 100
    override fun pointsRemovedPerWrongAnswer(): Int = 0
}