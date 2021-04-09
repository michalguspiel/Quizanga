package com.erdees.quizanga

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WelcomeFragmentInstrumentedTest {
    private lateinit var quizangaApplication: QuizangaApplication
    private lateinit var scenario : ActivityScenario<MainActivity>
    private lateinit var startGameButton : Matcher<View>
    private val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
    @Before
    fun setup(){
        quizangaApplication = QuizangaApplication()
        startGameButton = ViewMatchers.withId(R.id.view_welcome_start_new_game_button)
        scenario = ActivityScenario.launch(MainActivity::class.java)

    }
    @After
    fun closeEverything(){
        scenario.close()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.erdees.quizanga", appContext.packageName)
        Assert.assertEquals(resources.getString(R.string.PLAYER_NUMBER_PICKER_ID).toInt(), 15)
    }

    @Test
    fun checkIfStartGameButtonWorks(){
        val setGameScreen = ViewMatchers.withId(R.id.set_game_fragment)
        Espresso.onView(startGameButton).perform(ViewActions.click())
        Espresso.onView(setGameScreen).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}