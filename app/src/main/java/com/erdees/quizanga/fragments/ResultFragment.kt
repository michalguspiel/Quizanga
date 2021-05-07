package com.erdees.quizanga.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.openFragmentWithoutBackStack
import com.erdees.quizanga.Utils.playSound
import com.erdees.quizanga.Utils.sortByPoints
import com.erdees.quizanga.Utils.testOpenFragment
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.models.Player


class ResultFragment : Fragment() {

    lateinit var application: QuizangaApplication
    private lateinit var third : TextView
    private lateinit var winner : Player
    private lateinit var secondPlace : Player
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_fragment, container, false)

        val first = view.findViewById<TextView>(R.id.result_first)
        val second = view.findViewById<TextView>(R.id.result_second)
        third = view.findViewById(R.id.result_third)
        val button = view.findViewById<Button>(R.id.result_button)

        val playerList = application.game.players

            winner = playerList.sortByPoints()[0]
            secondPlace = playerList.sortByPoints()[1]
            if(playerList.size < 3) third.visibility = View.GONE
            else showThird(playerList)
            first.text = "${winner.name} with ${winner.points} points."
            second.text = "2nd. ${secondPlace.name} with ${secondPlace.points} points."

        playSound(R.raw.game_ended, this.requireContext())

        button.setOnClickListener {
            restartGame()
            openSetGameFragment()
        }
        return view
    }

    private fun openSetGameFragment(){
        val setGameFragment = SetGameFragment.newInstance()
        setGameFragment.application = application
        testOpenFragment(setGameFragment,SetGameFragment.TAG,parentFragmentManager)
    }

    @SuppressLint("SetTextI18n")
    private fun showThird(list: List<Player>){
        val thirdPlace = list.sortByPoints()[2]
        third.text = "3rd. ${thirdPlace.name} with ${thirdPlace.points} points."
    }

    private fun restartGame(){
        application.restartGame()
    }

    companion object {
        fun newInstance(): ResultFragment = ResultFragment()
        const val TAG = "ResultFragment"
    }
}