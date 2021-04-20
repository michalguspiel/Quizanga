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

class BetweenQuestionFragment : Fragment() {

    lateinit var  application: QuizangaApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.between_question_fragment, container, false)

        val tv = view.findViewById<TextView>(R.id.between_question_tv)
        val button  = view.findViewById<Button>(R.id.between_question_button)

        tv.text = "Next question for " + application.game.players[application.game.currentTurnCounter].name + "."

        button.setOnClickListener {
            proceedWithQuestion()
        }

        return view
    }

    private fun proceedWithQuestion(){
            application.proceedWithQuestion()
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

    companion object {
        fun newInstance(): BetweenQuestionFragment = BetweenQuestionFragment()
        const val TAG = "BetweenQuestionFragment"
    }
}
