package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erdees.quizanga.QuizangaApplication
import com.erdees.quizanga.R

class GameFragment: Fragment() {

    lateinit var application: QuizangaApplication


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_fragment,container,false)

        val test = view.findViewById<TextView>(R.id.textView)
        test.text = application.game.players.map { it.name }.joinToString { it } + application.game.numberOfTurns.toString() + application.game.difficultLevel.name

        return view
    }


    companion object{
        const val TAG = "GameFragment"
        fun newInstance() : GameFragment = GameFragment()
    }
}