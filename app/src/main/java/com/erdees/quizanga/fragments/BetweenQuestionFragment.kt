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
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils.openFragment
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.screens.GameQuestionScreen
import com.erdees.quizanga.viewModels.BetweenQuestionFragmentViewModel

class BetweenQuestionFragment : Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var viewModel : BetweenQuestionFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.between_question_fragment, container, false)
        viewModel = ViewModelProvider(this).get(BetweenQuestionFragmentViewModel::class.java)
        val tv = view.findViewById<TextView>(R.id.between_question_tv)
        val button  = view.findViewById<Button>(R.id.between_question_button)

        viewModel.getPlayersForThisGame(application.game.gameId).observe(viewLifecycleOwner,{ players ->
            tv.text = "Next question for " + players[application.game.currentTurnCounter].name + "."
        })

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
