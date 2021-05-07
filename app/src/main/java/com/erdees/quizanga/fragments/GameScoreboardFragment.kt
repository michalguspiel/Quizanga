package com.erdees.quizanga.fragments

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.openFragmentWithoutBackStack
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.viewModels.GameScoreboardFragmentViewModel

class GameScoreboardFragment : Fragment() {

    lateinit var application: QuizangaApplication
    private lateinit var scoreBoardLayout: LinearLayout
    private lateinit var viewModel: GameScoreboardFragmentViewModel
    private lateinit var button: Button
    private lateinit var roundsLeftTV: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_scoreboard_fragment, container, false)
        viewModel = ViewModelProvider(this).get(GameScoreboardFragmentViewModel::class.java)
        scoreBoardLayout = view.findViewById(R.id.game_fragment_linear_layout)
        button = view.findViewById(R.id.game_scoreboard_start_game)
        roundsLeftTV = view.findViewById(R.id.game_scoreboard_rounds_left)
        viewModel.getPlayersForThisGame(application.game.gameId).observe(viewLifecycleOwner, {
            setScoreboard(it)
            application.game.players = it as MutableList<Player>
        })
        roundsLeftTV.text = roundsLeft()

        button.setOnClickListener {
            startRound()
        }

        return view
    }

    private fun roundsLeft(): String {
        return if (application.game.numberOfTurnsLeft <= 1) "Last round!"
        else "${application.game.numberOfTurnsLeft} rounds left."
    }

    private fun startRound() {
        application.proceedWithQuestion()
        val fragment = GameQuestionFragment.newInstance()
        fragment.application = application
        openFragmentWithoutBackStack(fragment, GameQuestionFragment.TAG, this.parentFragmentManager)

    }

    @SuppressLint("InflateParams")
    private fun setScoreboard(playerList: List<Player>) {
        for (eachPlayer in 0 until (playerList.size)) {
            val inflater =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_game_scoreboard, null)
            scoreBoardLayout.addView(inflater)
            if (eachPlayer == playerList.size - 1) inflater.findViewById<TableRow>(R.id.scoreboard_table_row).visibility =
                View.GONE
        }
        populateScoreboard(playerList)
    }

    @SuppressLint("SetTextI18n")
    private fun populateScoreboard(playerList: List<Player>) {
        for (eachPlayer in 0 until (playerList.size)) {
            val getView = scoreBoardLayout.getChildAt(eachPlayer)
            val name = getView.findViewById<TextView>(R.id.scoreboard_name)
            val points = getView.findViewById<TextView>(R.id.scoreboard_points)
            name.text = playerList[eachPlayer].name
            points.text = playerList[eachPlayer].points.toString() + " points."
        }

    }

    companion object {
        const val TAG = "GameScoreboardFragment"
        fun newInstance(): GameScoreboardFragment = GameScoreboardFragment()
    }
}