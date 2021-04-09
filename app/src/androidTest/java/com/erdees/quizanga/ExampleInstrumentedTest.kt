package com.erdees.quizanga

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var quizangaApplication: QuizangaApplication
    private lateinit var scenario : ActivityScenario<MainActivity>

@Before
fun setup(){
    quizangaApplication = QuizangaApplication()
    scenario = ActivityScenario.launch(MainActivity::class.java)
}

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.erdees.quizanga", appContext.packageName)
    }

    @Test
    fun checkIfStartGameButtonWorks(){
        val startGameButton = withId(R.id.view_welcome_start_new_game_button)
        val setGameScreen = withId(R.id.set_game_tv)
        onView(startGameButton).perform(click())
        onView(setGameScreen).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun checkIfStartingNewGameWorks(){

    }


}