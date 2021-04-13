package com.erdees.quizanga.levelOfDifficult

class Easy(override var name: String = "Easy") : LevelOfDifficult {

    override fun pointsRemovedPerWrongAnswer(): Int = 0
    override fun pointsAddedForCorrectAnswer(): Int = 100

}