package com.erdees.quizanga.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erdees.quizanga.R
import com.erdees.quizanga.gameLogic.QuizangaApplication

class LoadingFragment : Fragment() {

    lateinit var application: QuizangaApplication

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loading_fragment, container, false)

        return view
    }

    companion object {
        fun newInstance() : LoadingFragment = LoadingFragment()
        const val TAG = "LoadingFragment"
    }
}