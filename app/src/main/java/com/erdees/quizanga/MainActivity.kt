package com.erdees.quizanga

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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

    /**Setting players in Game object accordingly to active game state
     * function load screen is inside getQuestions().observe because otherwise function loadScreen is called before questions are downlaoded.
     * Function load screen is in here because Screen needs to be refreshed every time any player gets updated.
     *
     * For now it works like this, because I'm using room and liveData, basically I need refreshed [GameState] and [Player] every time I'm loading new fragment
     * I could load it manually every time question is answered but then anyways liveData has to refresh fragment because if I get it from .value it's gonna return
     * NULL. I hate how it works but for now It will stay like this, I have no idea yet how to improve it. TODO*/
    private fun setApplicationGamePlayersFromGameState(playerList: List<Player>) {
        with(quizangaApplication.game) {
            players = playerList
            playersAmount = playerList.size
        }
        viewModel.getQuestions().observe(this,  { questions ->
            if(questions != null) loadScreen()
        })
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
            if(allQuestions == null){Log.i(TAG,"questions tried to be added")
                viewModel.addQuestions(quizangaApplication.game.difficultLevel)
            }
        })
        viewModel.getPlayersFromThisGameState(gameState.gameId).observe(  this , { playerList ->
            setApplicationGamePlayersFromGameState(playerList)
        })
    }


     fun loadScreen() {
        Log.i(TAG,"Load screen casted!")
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