package com.erdees.quizanga

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.Utils.ignoreFirst
import com.erdees.quizanga.Utils.openFragment
import com.erdees.quizanga.Utils.openFragmentWithoutBackStack
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
    lateinit var state: GameState

    override fun onBackPressed() {
        Log.i(TAG, "on back ${supportFragmentManager.backStackEntryCount}    ${setGameFragmentIsVisible()}")
        if (setGameFragmentIsVisible() == true && supportFragmentManager.backStackEntryCount == 1) {
            Log.i(TAG,"CASTED½! BACK !!")
            val fragment = WelcomeFragment.newInstance()
            fragment.application = quizangaApplication
            openFragmentWithoutBackStack(fragment, WelcomeFragment.TAG,supportFragmentManager)
        }
        else if (gamesHistoryIsVisible() == true || setGameFragmentIsVisible() == true) {
            super.onBackPressed()
            return
        }
        else {
            val dialog = AlertDialog.Builder(this)
                .setPositiveButton("Restart Game", null)
                .setNeutralButton("Back", null)
                .setNegativeButton("Exit Quizanga", null)
                .show()
            dialog.window?.setLayout(700, dialog.window!!.attributes.height)
            val backButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            val exitButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            val restartGameButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            exitButton.setExitButton(dialog, this)
            restartGameButton.setRestartGameButton(dialog)
            backButton.setBackButton(dialog)
        }
    }

    private lateinit var viewModel: MainActivityViewModel

    override fun onResume() {
        loadingFragment.application = quizangaApplication
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingFragment.application = quizangaApplication
        openFragment(loadingFragment, LoadingFragment.TAG,supportFragmentManager) //Open loading fragment while data is fetched from database.
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getDataAboutProblems().ignoreFirst().observe(this, {
                quizangaApplication.hasProblemOccurred = it
                setScreen()
                loadScreen()
            })

        viewModel.getActiveGameState().observe(this, { gameState ->
            if (gameState != null){
                setApplicationGameObjectAsGameState(gameState)
                state = gameState
            }
            else {
                setScreen()
                loadScreen()
            }
        })
    }


    private fun setApplicationGamePlayersFromGameState(playerList: MutableList<Player>) {
        with(quizangaApplication.game) {
            players = playerList
            playersAmount = playerList.size
        }
    }

    private fun setApplicationGameObjectAsGameState(gameState: GameState) {
        with(quizangaApplication.game) {
            gameId = gameState.gameId
            hasStarted = true
            difficultLevel = gameState.difficultyLevel
            numberOfTurnsLeft = gameState.numberOfTurnsLeft
            currentTurnCounter = gameState.roundCounter
        }
        viewModel.getQuestions().observe(this,{ allQuestions ->
            if(allQuestions == null){
                viewModel.addQuestions(quizangaApplication.game.difficultLevel)
            }
        })
        viewModel.getPlayersFromThisGameState(gameState.gameId).observe(  this , { playerList ->
            setApplicationGamePlayersFromGameState(playerList as MutableList<Player>)
        })
    }


     private fun setScreen() = quizangaApplication.setScreen()
     private fun loadScreen() {
        Log.i(TAG,"Load screen casted!")
            when (quizangaApplication.screen) {
                is WelcomeScreen -> {
                    val fragment = WelcomeFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragmentWithoutBackStack(fragment, WelcomeFragment.TAG,supportFragmentManager)
                }
                is SetGameScreen -> {
                    val fragment = SetGameFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragmentWithoutBackStack(fragment, SetGameFragment.TAG,supportFragmentManager)
                }
                is GameScoreboardScreen -> {
                    val fragment = GameScoreboardFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragmentWithoutBackStack(fragment, GameScoreboardFragment.TAG,supportFragmentManager)
                }
                is GameQuestionScreen -> {
                    val fragment = GameQuestionFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragmentWithoutBackStack(fragment, GameQuestionFragment.TAG,supportFragmentManager)
                }
                is LoadingScreen -> {
                    val fragment = LoadingFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, LoadingFragment.TAG,supportFragmentManager)
                }
                is ResultScreen -> {
                    val fragment = ResultFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragmentWithoutBackStack(fragment, ResultFragment.TAG,supportFragmentManager)
                }
        }
    }

    private fun Button.setBackButton(dialog : AlertDialog){
        val backButtonLP : LinearLayout.LayoutParams = this.layoutParams as LinearLayout.LayoutParams
        backButtonLP.gravity = Gravity.CENTER
        this.layoutParams = backButtonLP
        this.setOnClickListener { dialog.dismiss() }
    }

    private fun Button.setExitButton(dialog: AlertDialog,activity: MainActivity){
        val exitButtonLP : LinearLayout.LayoutParams = this.layoutParams as LinearLayout.LayoutParams
        exitButtonLP.gravity = Gravity.CENTER
        this.layoutParams = exitButtonLP
        this.setOnClickListener {
            dialog.dismiss()
            activity.finish()
        }
    }
    private fun Button.setRestartGameButton(dialog: AlertDialog){
        val restartGameButtonLP: LinearLayout.LayoutParams = this.layoutParams as LinearLayout.LayoutParams
        restartGameButtonLP.gravity = Gravity.CENTER
        this.layoutParams = restartGameButtonLP
        if(quizangaApplication.game.players.isEmpty()) this.visibility = View.GONE
        this.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }
    }
    private fun gamesHistoryIsVisible():Boolean?{
        return supportFragmentManager.findFragmentByTag("GamesHistory")?.isVisible
    }

    private fun setGameFragmentIsVisible() : Boolean? {
        return supportFragmentManager.findFragmentByTag("SetGameFragment")?.isVisible
    }

    private fun restartGame(){
        Log.i(TAG, quizangaApplication.game.hasStarted.toString())
        viewModel.deleteGameState(state)
        viewModel.deletePlayers(quizangaApplication.game.players)
        quizangaApplication.restartGame()
        Log.i(TAG, quizangaApplication.game.hasStarted.toString())
        loadScreen()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}