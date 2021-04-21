package com.erdees.quizanga

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.Utils.appWillSoonRunOutOfQuestions
import com.erdees.quizanga.Utils.openFragment
import com.erdees.quizanga.fragments.*
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.*
import com.erdees.quizanga.viewModels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val loadingFragment = LoadingFragment.newInstance()
    private val quizangaApplication by lazy {
        (application as MainApplication).quizangaApplication
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
            .setPositiveButton("Exit Quizanga",null)
            .setNegativeButton("Back",null)
            .setNeutralButton("Restart game",null)
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        dialog.dismiss()
            this.finish()
        }
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener { Log.i(TAG,"this will reset game in future") } // TODO
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var frame: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(loadingFragment, LoadingFragment.TAG,supportFragmentManager) //Open loading fragment while data is fetched from database.

        frame = findViewById(R.id.activity_main_frame)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getActiveGameState().observe(this, { gameState ->
            Log.i(TAG,"Got another game state")
            if (gameState != null) setApplicationGameObjectAsGameState(gameState)
            else loadScreen()
        })
    }

    private fun setApplicationGamePlayersFromGameState(playerList: List<Player>) {
        with(quizangaApplication.game) {
            players = playerList
            playersAmount = playerList.size
        }
        loadScreen()
        Log.i(TAG,"setting players from game state ")
    }

    private fun setApplicationGameObjectAsGameState(gameState: GameState) {
        Log.i(TAG,"Game found" + quizangaApplication.game.players.size)
        with(quizangaApplication.game) {
            gameId = gameState.gameId
            hasStarted = true
            difficultLevel = gameState.difficultyLevel
            numberOfTurnsLeft = gameState.numberOfTurnsLeft
            currentTurnCounter = gameState.roundCounter
        }
        viewModel.getQuestions().observe(this,{ allQuestions ->
            if(allQuestions == null)viewModel.addQuestions(quizangaApplication.game.difficultLevel)
        })
        viewModel.getPlayersFromThisGameState(gameState.gameId).observe(this, { playerList ->
            setApplicationGamePlayersFromGameState(playerList)
        })
    }


    private fun loadScreen() {
        quizangaApplication.setScreen()
        quizangaApplication.withScreenCallback { screen ->
            when (screen) {
                is WelcomeScreen -> {
                    val fragment = WelcomeFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, WelcomeFragment.TAG,supportFragmentManager)
                }
                is SetGameScreen -> {
                    val fragment = SetGameFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, SetGameFragment.TAG,supportFragmentManager)
                }
                is GameScoreboardScreen -> {
                    val fragment = GameScoreboardFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, GameScoreboardFragment.TAG,supportFragmentManager)
                }
                is GameQuestionScreen -> {
                    val fragment = GameQuestionFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, GameQuestionFragment.TAG,supportFragmentManager)
                }
                is BetweenQuestionScreen -> {
                    val fragment = BetweenQuestionFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, BetweenQuestionFragment.TAG,supportFragmentManager)
                }
                is LoadingScreen -> {
                    val fragment = LoadingFragment.newInstance()
                    openFragment(fragment, LoadingFragment.TAG,supportFragmentManager)
                }
                is ResultScreen -> {
                    val fragment = ResultFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, ResultFragment.TAG,supportFragmentManager)
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}