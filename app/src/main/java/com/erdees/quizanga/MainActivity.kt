package com.erdees.quizanga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.erdees.quizanga.fragments.GameQuestionFragment
import com.erdees.quizanga.fragments.GameScoreboardFragment
import com.erdees.quizanga.fragments.SetGameFragment
import com.erdees.quizanga.fragments.WelcomeFragment
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.screens.GameQuestionScreen
import com.erdees.quizanga.screens.GameScoreboardScreen
import com.erdees.quizanga.screens.SetGameScreen
import com.erdees.quizanga.screens.WelcomeScreen
import com.erdees.quizanga.viewModels.MainActivityViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

     private val quizangaApplication by lazy {
        (application as MainApplication).quizangaApplication
    }

    override fun onBackPressed() {
        if(this.supportFragmentManager.backStackEntryCount == 1) this.finish()
        else super.onBackPressed()
    }

    private lateinit var viewModel : MainActivityViewModel
    private lateinit var frame : FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        frame = findViewById(R.id.activity_main_frame)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.getActiveGameState().observe(this,  { gameState ->
            if(gameState != null ) {
                setApplicationGameObjectAsGameState(gameState)
        viewModel.getPlayersFromThisGameState(gameState.gameId).observe(this, { playerList ->
            setApplicationGamePlayersToFromGameState(playerList)
        })
            }
            else Log.i(TAG,"Game not found!")
            loadScreen()
        })
        viewModel.addQuestions()
    }

    private fun setApplicationGamePlayersToFromGameState(playerList: List<Player>){
        with(quizangaApplication.game){
            players = playerList
            Log.i(TAG,quizangaApplication.game.players.joinToString ("   "){ it.name })
        }
    }
    private fun setApplicationGameObjectAsGameState(gameState: GameState){
        with(quizangaApplication.game) {
            Log.i(TAG, "Game found!!!" + gameState.gameId.toString())
            gameId = gameState.gameId
            hasStarted = true
            difficultLevel = gameState.difficultyLevel
            numberOfTurnsLeft = gameState.numberOfTurnsLeft
            currentTurnCounter = gameState.roundCounter
        }
    }

    private fun loadScreen() {
        quizangaApplication.setScreen()
        quizangaApplication.withScreenCallback { screen ->
            frame.removeAllViews()
            when (screen) {
                is WelcomeScreen -> {
                    val fragment = WelcomeFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment, WelcomeFragment.TAG)
                }
                is SetGameScreen -> {
                    val fragment = SetGameFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment,SetGameFragment.TAG)
                }
                is GameScoreboardScreen -> {
                    val fragment = GameScoreboardFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment,GameScoreboardFragment.TAG)
                }
                is GameQuestionScreen -> {
                    val fragment = GameQuestionFragment.newInstance()
                    fragment.application = quizangaApplication
                    openFragment(fragment,GameQuestionFragment.TAG)
                }
            }
        }

    }

    private fun openFragment(fragment: Fragment, fragmentTag: String) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //if fragment isn't in backStack, create it
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.activity_main_frame, fragment, fragmentTag)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }
    companion object{
        const val TAG = "MainActivity"
    }
}