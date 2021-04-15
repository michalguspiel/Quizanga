package com.erdees.quizanga

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.erdees.quizanga.dao.BasicDao
import com.erdees.quizanga.dao.GameStateDao
import com.erdees.quizanga.dao.PlayerDao
import com.erdees.quizanga.database.AppRoomDatabase
import com.erdees.quizanga.database.BasicDatabase
import com.erdees.quizanga.levelOfDifficult.Easy
import com.erdees.quizanga.levelOfDifficult.Hard
import com.erdees.quizanga.models.GameState
import com.erdees.quizanga.models.Player
import com.erdees.quizanga.repository.GameStateRepository
import com.erdees.quizanga.repository.PlayerRepository
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
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
    private lateinit var playerRepository: PlayerRepository
    private lateinit var playerDao: PlayerDao
    private lateinit var basicDao: BasicDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        stateDao = database.gameStateDao()
        basicDao = BasicDatabase.getInstance().basicDao
        playerDao = database.playerDao()
        stateRepository = GameStateRepository(stateDao, basicDao)
        playerRepository = PlayerRepository(playerDao)
    }

    @After
    fun tearDown() {
        database.close()
    }


    private var testGame = GameState(354L, 5, Hard, 1)

    private var firstPlayer = Player(123, 354L, name = "Michael", points = 0)
    private var secondPlayer = Player(312, 354L, name = "Jordan", points = 0)
    private var thirdPlayer = Player(7538, 354L, name = "Jackson", points = 0)
    private val playerList = listOf<Player>(firstPlayer, secondPlayer, thirdPlayer)

    @Test
    fun testIfGameStateWillBeAddedToDatabase() = runBlocking {
        stateDao.startGame(testGame)
        val game = stateDao.getActiveGame().getOrAwaitValue()
        assertThat(game.gameId).isEqualTo(354)
    }

    @Test
    fun addGameWithoutRoundsLeft_TryToGetIt_ShouldReturnNull() = runBlocking {
        val gameWithoutRounds = GameState(0, 0, Easy, 1)
        stateDao.startGame(gameWithoutRounds)
        val game = stateDao.getActiveGame().getOrAwaitValue()
        assertThat(game).isEqualTo(null)
    }

    @Test
    fun testRepository() = runBlocking {
        stateRepository.startGame(testGame)
        val game = stateRepository.getActiveGame().getOrAwaitValue()
        assertThat(game.gameId).isEqualTo(354)
    }

    @Test
    fun addingGameStateToDatabase_ShouldReturnIdOfTable() = runBlocking {
        assertEquals(stateRepository.startGame(testGame), 354L)
    }

    @Test
    fun addingGameStateToDatabase_UpdatesLastAddedGameId() = runBlocking {
        stateRepository.startGame(testGame)
        assertEquals(354L, stateRepository.lastAddedGameId().value)
    }

    @Test
    fun addingPlayerListToDatabase_EachOfThemShouldHaveReferenceToSameGameID() = runBlocking {
        stateRepository.startGame(testGame)
        playerRepository.savePlayersIntoGame(playerList)
        val playerListFromDatabase =
            playerRepository.getPlayersFromGame(testGame.gameId).getOrAwaitValue()
        assertEquals(playerList, playerListFromDatabase)
    }

    @Test
    fun changingGameTurnCount_ByUpdate_ShouldChangeGameCount() = runBlocking {
        stateRepository.startGame(testGame)
        playerRepository.savePlayersIntoGame(playerList)
        testGame.roundCounter++
        stateRepository.updateGame(testGame)
        val updatedGame = stateRepository.getActiveGame().getOrAwaitValue()
        assertEquals(updatedGame.roundCounter, 2)
    }

    @Test
    fun reducingNumbersOfTurnLeft_ByUpdate_ShouldChangeNumbersOfRoundsLeft() = runBlocking {
        stateRepository.startGame(testGame)
        playerRepository.savePlayersIntoGame(playerList)
        testGame.numberOfTurnsLeft--
        stateRepository.updateGame(testGame)
        val updatedGame = stateRepository.getActiveGame().getOrAwaitValue()
        assertEquals(updatedGame.numberOfTurnsLeft, 4)
    }

    @Test
    fun addingPlayerPoints_ByUpdate_ShouldAddPointsDependingOnDifficultyLevel() = runBlocking {
        stateRepository.startGame(testGame)
        playerRepository.savePlayersIntoGame(playerList)
        val playerToAddPoints = playerList.first()
        playerToAddPoints.points =
            playerToAddPoints.pointsAfterCorrectAnswer(testGame.difficultyLevel.pointsAddedForCorrectAnswer())
        playerRepository.updatePoints(playerToAddPoints)
        val samePlayerButDownloadedFromDatabase =
            playerRepository.getPlayersFromGame(testGame.gameId).getOrAwaitValue().first()
        assertEquals(
            testGame.difficultyLevel.pointsAddedForCorrectAnswer(),
            samePlayerButDownloadedFromDatabase.points
        )
    }

    @Test
    fun removingPlayerPoints_ByUpdate_ShouldRemovePointsDependingOnDifficultyLevel() = runBlocking {
        stateRepository.startGame(testGame)
        playerRepository.savePlayersIntoGame(playerList)
        val playerToRemovePoints = playerList.last()
        playerToRemovePoints.points =
            playerToRemovePoints.pointsAfterWrongAnswer(testGame.difficultyLevel.pointsRemovedPerWrongAnswer())
        playerRepository.updatePoints(playerToRemovePoints)
        val samePlayerButDownloadedFromDatabase =
            playerRepository.getPlayersFromGame(testGame.gameId).getOrAwaitValue().last()
        assertEquals(
            -testGame.difficultyLevel.pointsRemovedPerWrongAnswer(),
            samePlayerButDownloadedFromDatabase.points
        )
    }

    @Test
    fun whenGameDifficultyIsEasy_WrongAnswer_ShouldNotRemovePoints() = runBlocking {
        val easyGame = GameState(333, 2, Easy, 1)
        stateRepository.startGame(easyGame)
        val easyGamePlayer1 = Player(1,333L,"EasyPlayer1",0)
        val easyGamePlayer2 = Player(2,333L,"EasyPlayer2",0)
        val playerList = listOf(easyGamePlayer1,easyGamePlayer2)
        playerRepository.savePlayersIntoGame(playerList)
        assertEquals(playerRepository.getPlayersFromGame(333).getOrAwaitValue().size,2)
        easyGamePlayer1.points = easyGamePlayer1.pointsAfterWrongAnswer(easyGame.difficultyLevel.pointsRemovedPerWrongAnswer())
        playerRepository.updatePoints(easyGamePlayer1)
        val firstPlayerButFromDatabase = playerRepository.getPlayersFromGame(easyGame.gameId).getOrAwaitValue().first()
        assertEquals(firstPlayerButFromDatabase.points,0)
    }
}