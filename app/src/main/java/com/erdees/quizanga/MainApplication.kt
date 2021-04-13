package com.erdees.quizanga

import android.app.Application

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
