package com.erdees.quizanga.levelOfDifficult


interface LevelOfDifficult {
    var name: String
    fun pointsRemovedPerWrongAnswer(): Int
    fun pointsAddedForCorrectAnswer(): Int

}


