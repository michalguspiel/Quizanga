package com.erdees.quizanga.repository

import android.util.Log
import com.erdees.quizanga.dao.BasicDao
import com.erdees.quizanga.gameLogic.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.models.Questions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasicRepository(val dao: BasicDao) {

    private val questionRepository = QuestionRepository()

    fun setAmountOfPlayers(number: Int) = dao.setAmountOfPlayers(number)
    fun getAmountOfPlayers() = dao.getAmountOfPlayers()

    fun setAmountOfGameTurns(number: Int) = dao.setAmountOfGameTurns(number)
    fun getAmountOfGameTurns() = dao.getAmountOfGameTurns()

    fun setLevelOfDifficulty(level: LevelOfDifficult) = dao.setLevelOfDifficulty(level)
    fun getDifficultLevel() = dao.getDifficultLevel()

    fun getQuestions() = dao.getQuestion()

    fun getDataAboutProblemsWithConnectionOrQuestions() = dao.getDataAboutProblemWithConnectionOrQuestions()

    fun setQuestionsOrAddIfLiveDataAlreadyExists(difficult: LevelOfDifficult){
        val currentQuestionsLiveData = dao.getQuestion()
        val question = questionRepository.getQuestion(difficult)
        question.enqueue(object : Callback<Questions> {
            override fun onResponse(call: Call<Questions>, response: Response<Questions>) {
                if(response.isSuccessful) {
                    Log.i("QUESTION", "WORKED!")
                    val result = response.body()!!
                    if(currentQuestionsLiveData.value == null)dao.setQuestions(result)
                    else dao.addQuestions(result)
                    dao.setDataAboutProblems(hasProblemOccurred = false)
                }
            }
            override fun onFailure(call: Call<Questions>, t: Throwable) {
                Log.i("QUESTION", "Failed ${t.message}")
                dao.setDataAboutProblems(hasProblemOccurred = true)
            }
        })
    }



}