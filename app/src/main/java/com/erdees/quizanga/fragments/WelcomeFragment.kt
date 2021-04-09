package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.erdees.quizanga.QuizangaApplication
import com.erdees.quizanga.R

class WelcomeFragment: Fragment() {
    lateinit var application : QuizangaApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.welcome_fragment,container,false)

        val startNewGameButton = view.findViewById<Button>(R.id.view_welcome_start_new_game_button)

        startNewGameButton.setOnClickListener {
            application.startGame()
        }

        return view
    }

   companion object {
    const val TAG = "WelcomeFragment"
    fun newInstance() : WelcomeFragment = WelcomeFragment()
}

}