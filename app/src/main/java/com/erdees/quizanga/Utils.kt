package com.erdees.quizanga

import android.app.ActionBar
import android.content.Context
import android.media.MediaPlayer
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.erdees.quizanga.Utils.addMargin
import com.erdees.quizanga.models.Player

object Utils {

    fun openFragment(fragment: Fragment, fragmentTag: String, fragmentManager: FragmentManager) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = fragmentManager
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.activity_main_frame, fragment, fragmentTag)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(backStateName)
            ft.commit()
    }

     fun appWillSoonRunOutOfQuestions(numberOfQuestions: Int, sessionQuestionCounter : Int): Boolean {
        return (numberOfQuestions <= sessionQuestionCounter + 2)
    }

    fun Button.addMargin(dp : Int){
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(dp)
        this.layoutParams = layoutParams
    }

    fun playSound(soundResource: Int,context : Context){
        val mp = MediaPlayer.create(context,soundResource)
        mp.setOnCompletionListener { it.release() }
        mp.start()
    }

    fun List<Player>.sortByPoints(): List<Player>{
        return this.sortedBy { it.points }.reversed()
    }

}