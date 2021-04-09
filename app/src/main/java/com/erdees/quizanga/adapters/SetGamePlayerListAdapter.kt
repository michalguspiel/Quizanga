package com.erdees.quizanga.adapters

import android.app.Activity
import android.widget.ArrayAdapter
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.R

class SetGamePlayerListAdapter(val activity: Activity, val list: List<Player>):ArrayAdapter<Player>(activity, R.layout.item_set_game_player_list) {
}