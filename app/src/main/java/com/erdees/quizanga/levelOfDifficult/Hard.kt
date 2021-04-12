package com.erdees.quizanga.levelOfDifficult

class Hard : LevelOfDifficult {
    override fun pointsAddedForCorrectAnswer(): Int = 100
    override fun pointsRemovedPerWrongAnswer(): Int = 50
}