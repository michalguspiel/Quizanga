package com.erdees.quizanga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erdees.quizanga.gameLogic.QuizangaApplication
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.viewModels.SetGameFragmentViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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