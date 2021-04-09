package com.erdees.quizanga.database

import com.erdees.quizanga.dao.BasicDao

class BasicDatabase private constructor() {

    var basicDao = BasicDao()

    /**Singleton object */
    companion object {
        @Volatile
        private var instance: BasicDatabase? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: BasicDatabase().also { instance = it }

            }
    }
}