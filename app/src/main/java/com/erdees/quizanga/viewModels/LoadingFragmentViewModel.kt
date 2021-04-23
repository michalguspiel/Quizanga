package com.erdees.quizanga.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.repository.BasicRepository

class LoadingFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val basicRepository : BasicRepository
    init {
        val basicDao = BasicDatabase.getInstance().basicDao
        basicRepository = BasicRepository(basicDao)
    }

    fun addQuestions(difficult: LevelOfDifficult) = basicRepository.setQuestionsOrAddIfLiveDataAlreadyExists(difficult)
}