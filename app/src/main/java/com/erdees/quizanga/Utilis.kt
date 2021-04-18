package com.erdees.quizanga

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

 fun openFragment(fragment: Fragment, fragmentTag: String, fragmentManager: FragmentManager) {
    val backStateName = fragment.javaClass.name
    val manager: FragmentManager = fragmentManager
    val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
    if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //if fragment isn't in backStack, create it
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.activity_main_frame, fragment, fragmentTag)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.addToBackStack(backStateName)
        ft.commit()
    }
}