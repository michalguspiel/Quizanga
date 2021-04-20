package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erdees.quizanga.R
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.openFragment
import com.erdees.quizanga.screens.GameQuestionScreen
import com.erdees.quizanga.screens.SetGameScreen

class ResultFragment : Fragment() {

    lateinit var application: QuizangaApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_fragment, container, false)
        val first = view.findViewById<TextView>(R.id.result_first)
        val second = view.findViewById<TextView>(R.id.result_second)
        val third = view.findViewById<TextView>(R.id.result_third)
        val button = view.findViewById<Button>(R.id.result_button)

        first.text = "1st. " +  application.game.playersInOrderOfPoints()[0].name
        second.text = "2nd. " + application.game.playersInOrderOfPoints()[1].name
        if(application.game.playersAmount < 3) third.visibility = View.GONE
        else third.text = "3rd. " + application.game.playersInOrderOfPoints()[2].name


        button.setOnClickListener {
            application.screen = SetGameScreen()
            application.withScreenCallback { screen ->
                when (screen) {
                    is SetGameScreen -> {
                        val fragment = SetGameFragment.newInstance()
                        fragment.application = application
                        openFragment(fragment,SetGameFragment.TAG,this.parentFragmentManager)
                    }
                }
            }

        }




        return view

    }

    companion object {
        fun newInstance(): ResultFragment = ResultFragment()
        const val TAG = "ResultFragment"
    }
}