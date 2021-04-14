package com.erdees.quizanga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.getOrAwaitValue
import com.erdees.quizanga.repository.GameStateRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomDatabaseTests {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppRoomDatabase
    private lateinit var stateDao: GameStateDao
    private lateinit var stateRepository: GameStateRepository

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        stateDao = database.gameStateDao()
        stateRepository = GameStateRepository(stateDao)
    }
    @After
    fun tearDown(){
        database.close()
    }


    private val testGame = GameState(354,5, Hard , 1)

    @Test
     fun testIfGameStateWillBeAddedToDatabase() = runBlocking {
        stateDao.startGame(testGame)
        val game = stateDao.getActiveGame().getOrAwaitValue()
        assertThat(game.gameId).isEqualTo(354)
    }
    @Test
    fun addGameWithoutRoundsLeft_TryToGetIt_ShouldReturnNull(){
        stateDao.startGame(testGame)
        val game = stateDao.getActiveGame().getOrAwaitValue()
        assertThat(game).isEqualTo(null)
    }

    @Test
    fun testRepository() = runBlocking {
        stateRepository.startGame(testGame)
        val game = stateRepository.getActiveGame().getOrAwaitValue()
        assertThat(game.gameId).isEqualTo(354)
    }

}