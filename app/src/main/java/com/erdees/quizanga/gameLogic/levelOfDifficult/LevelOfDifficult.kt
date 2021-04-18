package com.erdees.quizanga.gameLogic.levelOfDifficult


interface LevelOfDifficult {
    var name: String
    fun pointsRemovedPerWrongAnswer(): Int
    fun pointsAddedForCorrectAnswer(): Int

}


