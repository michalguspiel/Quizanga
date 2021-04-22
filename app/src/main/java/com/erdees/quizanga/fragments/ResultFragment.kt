package com.erdees.quizanga.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.openFragment
import com.erdees.quizanga.Utils.playSound
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.screens.SetGameScreen

class ResultFragment : Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var third : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_fragment, container, false)
        val first = view.findViewById<TextView>(R.id.result_first)
        val second = view.findViewById<TextView>(R.id.result_second)
        third = view.findViewById<TextView>(R.id.result_third)
        val button = view.findViewById<Button>(R.id.result_button)
        val winner = application.game.playersInOrderOfPoints()[0]
        val secondPlace = application.game.playersInOrderOfPoints()[1]

        first.text = "${winner.name} with ${winner.points} points."
        second.text = "2nd. ${secondPlace.name} with ${secondPlace.points} points."
        if(application.game.playersAmount < 3) third.visibility = View.GONE
        else showThird()



        playSound(R.raw.game_ended,this.requireContext())

        button.setOnClickListener {
            restartGame()
            application.screen = SetGameScreen()
            application.withScreenCallback { screen ->
                when (screen) {
                    is SetGameScreen -> {
                        Log.i(TAG,"screen callback casted")
                        val fragment = SetGameFragment.newInstance()
                        fragment.application = application
                        openFragment(fragment,SetGameFragment.TAG,this.parentFragmentManager)
                    }
                }
            }

        }
        return view
    }

    private fun showThird(){
        val thirdPlace = application.game.playersInOrderOfPoints()[2]
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