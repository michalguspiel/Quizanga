package com.erdees.quizanga.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.R
import com.erdees.quizanga.Utils
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.viewModels.LoadingFragmentViewModel

class LoadingFragment : Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var handler: Handler
    lateinit var viewModel : LoadingFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loading_fragment, container, false)
        val tv = view.findViewById<TextView>(R.id.textView)
        viewModel = ViewModelProvider(this).get(LoadingFragmentViewModel::class.java)
        if(application.hasProblemOccurred) tv.text = "Problem has occurred check internet connection."
        handler = Handler()
        val delay = 5000L
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewModel.addQuestions(application.game.difficultLevel)
                handler.postDelayed(this, delay)
            }
        }, delay)

        return view
    }

    override fun onStop() {
        Log.i(TAG,"on stop !")
        handler.removeCallbacksAndMessages(null)
        super.onStop()
    }
    companion object {
        fun newInstance() : LoadingFragment = LoadingFragment()
        const val TAG = "LoadingFragment"
    }
}