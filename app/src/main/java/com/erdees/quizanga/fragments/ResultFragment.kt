package com.erdees.quizanga.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.MainActivity
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.openFragment
import com.erdees.quizanga.Utils.playSound
import com.erdees.quizanga.Utils.sortByPoints
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.SetGameScreen
import com.erdees.quizanga.viewModels.ResultFragmentViewModel

class ResultFragment : Fragment() {

    val setGameFragment = SetGameFragment.newInstance()
    lateinit var application: QuizangaApplication
    lateinit var third : TextView
    lateinit var winner : Player
    lateinit var secondPlace : Player
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_fragment, container, false)
        val viewModel = ViewModelProvider(this).get(ResultFragmentViewModel::class.java)

        val first = view.findViewById<TextView>(R.id.result_first)
        val second = view.findViewById<TextView>(R.id.result_second)
        third = view.findViewById(R.id.result_third)
        val button = view.findViewById<Button>(R.id.result_button)

        viewModel.getPlayersForThisGame(application.game.gameId).observe(viewLifecycleOwner,{
            playerList ->
            winner = playerList.sortByPoints()[0]
            secondPlace = playerList.sortByPoints()[1]
            if(playerList.size < 3) third.visibility = View.GONE
            else showThird(playerList)
            first.text = "${winner.name} with ${winner.points} points."
            second.text = "2nd. ${secondPlace.name} with ${secondPlace.points} points."
        })


        playSound(R.raw.game_ended,this.requireContext())

        button.setOnClickListener {
            restartGame()
            openSetGameFragment()
        }
        return view
    }

    private fun openSetGameFragment(){
        setGameFragment.application = application
        openFragment(setGameFragment,SetGameFragment.TAG,parentFragmentManager)
    }

    private fun showThird(list : List<Player>){
        val thirdPlace = list.sortByPoints()[2]
        third.text = "3rd. ${thirdPlace.name} with ${thirdPlace.points} points."
    }

    private fun restartGame(){
        application.restartGame()
        Log.i(TAG,  application.game.gameId.toString() + application.game.hasEnded.toString() + application.game.hasStarted.toString() + application.game.players.size.toString() )
    }

    companion object {
        fun newInstance(): ResultFragment = ResultFragment()
        const val TAG = "ResultFragment"
    }
}