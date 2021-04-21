package com.erdees.quizanga

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

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
}