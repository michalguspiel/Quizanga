package com.erdees.quizanga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.erdees.quizanga.fragments.SetGameFragment
import com.erdees.quizanga.fragments.WelcomeFragment

class MainActivity : AppCompatActivity() {

    private val quizangaApplication by lazy {
        (application as MainApplication).quizangaApplication
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frame = findViewById<FrameLayout>(R.id.activity_main_frame)

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
}