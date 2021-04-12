package com.erdees.quizanga

import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.erdees.quizanga.fragments.SetGameFragment
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SetUpGameInstrumentedTest {

    private lateinit var quizangaApplication: QuizangaApplication
    private lateinit var scenario : ActivityScenario<MainActivity>
    private lateinit var startGameButton : Matcher<View>
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val resources = context.resources

    @Before
fun setup(){
    quizangaApplication = QuizangaApplication()
    startGameButton = withId(R.id.view_welcome_start_new_game_button)
    scenario = ActivityScenario.launch(MainActivity::class.java)
}
@After
fun closeEverything(){
    scenario.close()
}

    @Test
    fun checkIfWhenSettingGameScreenIsOnAndBackButtonIsPressed_WillComeBackToWelcomeScreen(){
        val welcomeScreen = withId(R.id.welcome_fragment)
        val setGameScreen = withId(R.id.set_game_fragment)
        onView(welcomeScreen).check(matches(isDisplayed()))
        onView(startGameButton).perform(click())
        onView(setGameScreen).check(matches(isDisplayed()))
        pressBack()
        onView(welcomeScreen).check(matches(isDisplayed()))
    }

    @Test
    fun checkWhenPressedNumberOfPlayers_NumberPickerPopsUp(){
        val numberOfPlayersTV = withId(R.id.view_set_game_player_count)
        onView(startGameButton).perform(click())
        onView(numberOfPlayersTV).perform(click())
        onView(withText("Pick number of players")).check(matches(isDisplayed()))
    }

    @Test
    fun checkWhenPressedNumberOfTurns_NumberPickerPopsUp(){
        val numberOfTurnsTV = withId(R.id.view_set_game_number_of_turns_tv)
        onView(startGameButton).perform(click())
        onView(numberOfTurnsTV).perform(click())
        onView(withText("Pick number of turns")).check(matches(isDisplayed()))
    }

    private val clickTopCentre =
            actionWithAssertions(
                    GeneralClickAction(
                            Tap.SINGLE,
                            GeneralLocation.TOP_CENTER,
                            Press.FINGER,
                            InputDevice.SOURCE_UNKNOWN,
                            MotionEvent.BUTTON_PRIMARY
                    )
            )

    private val clickBottomCentre =
            actionWithAssertions(
                    GeneralClickAction(
                            Tap.SINGLE,
                            GeneralLocation.BOTTOM_CENTER,
                            Press.FINGER,
                            InputDevice.SOURCE_UNKNOWN,
                            MotionEvent.BUTTON_PRIMARY
                    )
            )
    @Test
    fun checkIfNumberOfPlayersWillBeSetFromDialog(){
        val numberPickerID = resources.getString(R.string.PLAYER_NUMBER_PICKER_ID).toInt()
        val alertDialogSubmitButtonID = resources.getString(R.string.PLAYER_DIALOG_BUTTON_ID).toInt()
        val numberOfPlayersTV = withId(R.id.view_set_game_player_count)
        val numberPicker = withId(numberPickerID)
        val numberPickerInput = withParent(withId(numberPickerID))
        val alertDialogSubmitButton = withId(alertDialogSubmitButtonID)

        onView(startGameButton).perform(click())
            onView(numberOfPlayersTV).perform(click())
            onView(withText("Pick number of players")).check(matches(isDisplayed()))
            onView(numberPicker).check(matches(isDisplayed()))
            onView(numberPickerInput).check(matches(withText("2")))
            onView(numberPicker).perform(clickBottomCentre)
            onView(numberPickerInput).check(matches(withText("3")))
            onView(alertDialogSubmitButton).perform(click())
            onView(withText("3")).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfNumberOfTurnsWillBeSetFromDialog(){
        val numberOfTurnsTV = withId(R.id.view_set_game_number_of_turns_tv)
        val numberPickerID = resources.getString(R.string.TURN_NUMBER_PICKER_ID).toInt()
        val numberPicker = withId(numberPickerID)
        val numberPickerInput = withParent(withId(numberPickerID))
        val alertDialogSubmitButtonID = resources.getString(R.string.TURN_DIALOG_BUTTON_ID).toInt()
        val alertDialogSubmitButton = withId(alertDialogSubmitButtonID)
        onView(startGameButton).perform(click())
        onView(numberOfTurnsTV).perform(click())
        onView(withText("Pick number of turns")).check(matches(isDisplayed()))
        onView(numberPickerInput).check(matches(withText("3")))
        onView(numberPicker).perform(clickTopCentre)
        onView(numberPickerInput).check(matches(withText("20")))
        onView(alertDialogSubmitButton).perform(click())
        onView(withText("20")).check(matches(isDisplayed()))
    }

    @Test
    fun accessGameFromFragment(){
        onView(startGameButton).perform(click())
        assertEquals(quizangaApplication.game.playersAmount,2)
    }


    @Test
    fun setupPlayersAmountInitially2() {
        onView(startGameButton).perform(click())
        onView(withText("2")).check(matches(isDisplayed()))
        quizangaApplication.open()
        quizangaApplication.game.setAmountOfPlayers(2)
        assertEquals(2, quizangaApplication.game.playersAmount)
    }

    @Test
    fun whileOpeningSetGameFragment_ListAdapterListShouldBeOfSize2(){
        onView(startGameButton).perform(click())
        onView(withText("2")).check(matches(isDisplayed()))

    }


}