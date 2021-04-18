package com.erdees.quizanga.viewModels

import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdees.quizanga.models.Question
import com.erdees.quizanga.repository.QuestionRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameQuestionFragmentViewModel: ViewModel() {

    val questionLiveData : MutableLiveData<Question> = MutableLiveData()
    val questionRepository  = QuestionRepository()

    fun getQuestion() {
        val question = questionRepository.getQuestion()
        question.enqueue(object : Callback<Question> {
            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                Log.i("QUESTION","WORKED!")
                Log.i("QUESTION","${response.isSuccessful}!")
                Log.i("QUESTION", response.body().toString())
                    val questionResponse = response.body()!!
                    questionLiveData.value = questionResponse

            }

            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.i("QUESTION", "Failed ${t.message}")
            }
        })
    }


}