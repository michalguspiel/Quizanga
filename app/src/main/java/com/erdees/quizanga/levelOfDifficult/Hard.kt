package com.erdees.quizanga.levelOfDifficult

class Hard(override var name: String = "Hard") : LevelOfDifficult {
    override fun pointsAddedForCorrectAnswer(): Int = 100
    override fun pointsRemovedPerWrongAnswer(): Int = 50
}