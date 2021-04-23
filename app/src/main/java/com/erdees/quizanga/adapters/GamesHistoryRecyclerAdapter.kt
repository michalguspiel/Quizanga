package com.erdees.quizanga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erdees.quizanga.R
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.models.StateAndPlayers

class GamesHistoryRecyclerAdapter(private val listOfGames: List<StateAndPlayers>, private val context: Context) : RecyclerView.Adapter<GamesHistoryRecyclerAdapter.ViewHolder>() {
        lateinit var layout: LinearLayout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game_history,parent,false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listOfGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val difficulty = holder.itemView.findViewById<TextView>(R.id.game_difficulty)
        layout = holder.itemView.findViewById(R.id.game_layout)
        difficulty.text = listOfGames[position].gameState.difficultyLevel.name + " game."
        setScoreboard(listOfGames[position].listOfPlayers)

    }


    private fun setScoreboard(playerList: List<Player>){
        layout.removeAllViews()  // TO CLEAR BEFORE ATTACHING NEW
        for (eachPlayer in 0 until(playerList.size)){
            val inflater = LayoutInflater.from(context).inflate(R.layout.item_game_scoreboard,null)
            layout.addView(inflater)
            if(eachPlayer == playerList.size-1) inflater.findViewById<TableRow>(R.id.scoreboard_table_row).visibility = View.GONE
        }
        populateScoreboard(playerList)
    }


    private fun populateScoreboard(playerList: List<Player>){
        for (eachPlayer in 0 until(playerList.size)){
            val getView = layout.getChildAt(eachPlayer)
            val name = getView.findViewById<TextView>(R.id.scoreboard_name)
            val points = getView.findViewById<TextView>(R.id.scoreboard_points)
            name.text = playerList[eachPlayer].name
            points.text = playerList[eachPlayer].points.toString() + " points."
        }

    }

}