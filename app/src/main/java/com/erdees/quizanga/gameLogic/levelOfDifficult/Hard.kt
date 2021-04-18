package com.erdees.quizanga.gameLogic.levelOfDifficult

object Hard: LevelOfDifficult {
    override var name: String = "Hard"
    override fun pointsAddedForCorrectAnswer(): Int = 100
    override fun pointsRemovedPerWrongAnswer(): Int = 50
}