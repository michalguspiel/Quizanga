package com.erdees.quizanga.models

import androidx.room.Embedded
import androidx.room.Relation


data class StateAndPlayers(
    @Embedded val gameState: GameState,
    @Relation(parentColumn = "gameId",
    entityColumn = "gameId")
    val listOfPlayers: List<Player>
) {
}