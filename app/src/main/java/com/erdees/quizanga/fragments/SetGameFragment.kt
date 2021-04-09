package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erdees.quizanga.QuizangaApplication
import com.erdees.quizanga.R

class SetGameFragment: Fragment() {

    lateinit var application: QuizangaApplication

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_set_game,container,false)
        return view
    }

    companion object {
        const val TAG = "SetGameFragment"
        fun newInstance(): SetGameFragment = SetGameFragment()
    }
}