package com.erdees.quizanga

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.levelOfDifficult.LevelOfDifficult
import com.erdees.quizanga.repository.BasicRepository
import com.erdees.quizanga.viewModels.SetGameFragmentViewModel
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

class ViewModelUnitTests {

    private lateinit var quizangaApplication: QuizangaApplication
    lateinit var  viewModel : SetGameFragmentViewModel

    @Rule @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpFragment(){
        quizangaApplication = QuizangaApplication()
        viewModel  = SetGameFragmentViewModel()
    }

    @Test
    fun setAmountOfPlayers_ShouldSetAmountOfPlayers(){
        viewModel.setAmountOfPlayers(5)
        assertEquals(viewModel.getAmountOfPlayers().value, 5)
    }

    @Test
    fun setAmountOfTurns_ShouldSetAmountOfTurns(){
        viewModel.setAmountOfGameTurns(99)
        assertEquals(viewModel.getAmountOfGameTurns().value,99)
    }

    @Test
    fun setLevelOfDifficulty_ShouldSetLevelOfDifficulty(){
        viewModel.setLevelOfDifficulty(Hard)
        assertEquals(Hard,viewModel.getDifficultLevel().value)
    }

}