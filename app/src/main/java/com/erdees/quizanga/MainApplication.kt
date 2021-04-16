package com.erdees.quizanga

import android.app.Application
import com.erdees.quizanga.gameLogic.QuizangaApplication

class MainApplication : Application() {
    val quizangaApplication by lazy {
        QuizangaApplication()
    }

    override fun onCreate() {
        super.onCreate()
        quizangaApplication.open()
        quizangaApplication.setScreen()
    }

}
