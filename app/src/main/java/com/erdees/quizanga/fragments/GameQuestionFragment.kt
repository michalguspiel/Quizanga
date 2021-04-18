package com.erdees.quizanga.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.MainActivity
import com.erdees.quizanga.R
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.network.GetDataService
import com.erdees.quizanga.network.RetrofitClientInstance
import com.erdees.quizanga.viewModels.GameQuestionFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameQuestionFragment:Fragment() {

    lateinit var application: QuizangaApplication


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_question_fragment,container,false)
        val playerNameTextView = view.findViewById<TextView>(R.id.game_question_player_name_tv)
        val questionTV = view.findViewById<TextView>(R.id.game_question_question)

        val viewModel = ViewModelProvider(this).get(GameQuestionFragmentViewModel::class.java)
        viewModel.getQuestion()

        viewModel.questionLiveData.observe(viewLifecycleOwner, Observer { question ->
            questionTV.text = question.question
        })


        playerNameTextView.text = "Question for " +  application.game.players[application.game.currentTurnCounter-1].name

        return view
    }

    companion object {
        const val TAG = "GameQuestionFragment"
        fun newInstance() : GameQuestionFragment = GameQuestionFragment()
    }
}