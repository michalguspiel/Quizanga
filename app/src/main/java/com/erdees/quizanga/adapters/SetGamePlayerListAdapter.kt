package com.erdees.quizanga.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.R

class SetGamePlayerListAdapter(context: Context,
                                val list: List<Player>):ArrayAdapter<Player>
    (context, R.layout.item_set_game_player_list) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

    fun getView(position: Int) : Any {
        return list[position]
    }

}