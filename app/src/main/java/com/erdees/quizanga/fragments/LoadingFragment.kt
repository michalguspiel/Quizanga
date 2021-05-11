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
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.*
import com.erdees.quizanga.viewModels.LoadingFragmentViewModel

class LoadingFragment : Fragment() {

    lateinit var application: QuizangaApplication
    lateinit var handler: Handler
    lateinit var viewModel: LoadingFragmentViewModel

    lateinit var state: GameState

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.loading_fragment, container, false)
        val tv = view.findViewById<TextView>(R.id.textView)
        viewModel = ViewModelProvider(this).get(LoadingFragmentViewModel::class.java)
        if (application.hasProblemOccurred) tv.text =
            "Problem has occurred check internet connection."
        handler = Handler()
        val delay = 5000L
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewModel.addQuestions(application.game.difficultLevel)
                handler.postDelayed(this, delay)
            }
        }, delay)

        viewModel.getDataAboutProblem().observe(viewLifecycleOwner, {
            if(!it) {
                viewModel.getActiveGameState().observe(viewLifecycleOwner, { gameState ->
                    if (gameState != null) {
                        setApplicationGameObjectAsGameState(gameState)
                        state = gameState
                        application.setScreen()
                        loadScreen()
                    } else {
                        application.setScreen()
                        loadScreen()
                    }
                })
            }
            })





        return view
    }


    private fun setApplicationGameObjectAsGameState(gameState: GameState) {
        with(application.game) {
            gameId = gameState.gameId
            hasStarted = true
            difficultLevel = gameState.difficultyLevel
            numberOfTurnsLeft = gameState.numberOfTurnsLeft
            currentTurnCounter = gameState.roundCounter
        }
        viewModel.getQuestions().observe(viewLifecycleOwner, { allQuestions ->
            if (allQuestions == null) {
                viewModel.addQuestions(application.game.difficultLevel)
            }
        })
        viewModel.getPlayersFromThisGameState(gameState.gameId)
            .observe(viewLifecycleOwner, { playerList ->
                setApplicationGamePlayersFromGameState(playerList as MutableList<Player>)
            })
    }

    private fun setApplicationGamePlayersFromGameState(playerList: MutableList<Player>) {
        Log.i(TAG," set players to game object casted!")
        with(application.game) {
            players = playerList
            playersAmount = playerList.size
        }
    }


    private fun loadScreen() {
        Log.i(TAG, "Load screen casted!")
        when (application.screen) {
            is WelcomeScreen -> {
                val fragment = WelcomeFragment.newInstance()
                fragment.application = application
                Utils.openFragmentWithoutAddingToBackStack(
                    fragment,
                    WelcomeFragment.TAG,
                    parentFragmentManager
                )
            }
            is GameScoreboardScreen -> {
                val fragment = GameScoreboardFragment.newInstance()
                fragment.application = application
                Utils.openFragmentWithoutAddingToBackStack(
                    fragment,
                    GameScoreboardFragment.TAG,
                    parentFragmentManager
                )
            }
            is GameQuestionScreen -> {
                val fragment = GameQuestionFragment.newInstance()
                fragment.application = application
                Utils.openFragmentWithoutAddingToBackStack(
                    fragment,
                    GameQuestionFragment.TAG,
                    parentFragmentManager
                )
            }
            is ResultScreen -> {
                val fragment = ResultFragment.newInstance()
                fragment.application = application
                Utils.openFragmentWithoutAddingToBackStack(
                    fragment,
                    ResultFragment.TAG,
                    parentFragmentManager
                )
            }
        }
    }

    override fun onStop() {
        Log.i(TAG, "on stop !")
        handler.removeCallbacksAndMessages(null)
        super.onStop()
    }

    companion object {
        fun newInstance(): LoadingFragment = LoadingFragment()
        const val TAG = "LoadingFragment"
    }
}