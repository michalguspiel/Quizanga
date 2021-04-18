package com.erdees.quizanga.fragments

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.R
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.openFragment
import com.erdees.quizanga.screens.GameQuestionScreen
import com.erdees.quizanga.screens.WelcomeScreen
import com.erdees.quizanga.viewModels.GameScoreboardFragmentViewModel

class GameScoreboardFragment: Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var scoreBoardLayout : LinearLayout
    lateinit var viewModel : GameScoreboardFragmentViewModel
    lateinit var button : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_scoreboard_fragment,container,false)
        viewModel = ViewModelProvider(this).get(GameScoreboardFragmentViewModel::class.java)
        scoreBoardLayout = view.findViewById(R.id.game_fragment_linear_layout)
        button = view.findViewById(R.id.game_scoreboard_start_game)
        viewModel.getPlayersForThisGame(application.game.gameId).observe(viewLifecycleOwner, {
                setScoreboard(it)
            })

        button.setOnClickListener {
        startRound()
        }

        return view
    }

    private fun startRound(){
        application.proceedWithQuestion()
        Log.i(TAG,application.screen.toString())
        Log.i(TAG,application.game.numberOfTurnsLeft.toString())
        application.withScreenCallback { screen ->
            when (screen) {
                is GameQuestionScreen -> {
                    val fragment = GameQuestionFragment.newInstance()
                    fragment.application = application
                    openFragment(fragment,GameQuestionFragment.TAG,this.parentFragmentManager)
                }
            }
        }
    }

    private fun setScoreboard(playerList: List<Player>){
        for (eachPlayer in 0 until(playerList.size)){
            val inflater = LayoutInflater.from(requireContext()).inflate(R.layout.item_game_scoreboard,null)
            scoreBoardLayout.addView(inflater)
        }
        populateScoreboard(playerList)
    }

    private fun populateScoreboard(playerList: List<Player>){
        for (eachPlayer in 0 until(playerList.size)){
            val getView = scoreBoardLayout.getChildAt(eachPlayer)
            val name = getView.findViewById<TextView>(R.id.scoreboard_name)
            val points = getView.findViewById<TextView>(R.id.scoreboard_points)
            name.text = playerList[eachPlayer].name
            points.text = playerList[eachPlayer].points.toString() + " points."
        }

    }

    companion object{
        const val TAG = "GameScoreboardFragment"
        fun newInstance() : GameScoreboardFragment = GameScoreboardFragment()
    }
}