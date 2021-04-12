package com.erdees.quizanga

import android.view.ViewGroup
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import com.erdees.quizanga.adapters.SetGamePlayerListAdapter
import com.erdees.quizanga.fragments.SetGameFragment
import com.erdees.quizanga.models.Player
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetGameListAdapterTest {

    lateinit var adapter : SetGamePlayerListAdapter
    lateinit var scenario: ActivityScenario<MainActivity>
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        val context = InstrumentationRegistry.getInstrumentation().context
        val michal = Player("Michal", 0)
        val bill = Player("Bill", 0)
        adapter = SetGamePlayerListAdapter(context, listOf(michal, bill))
    }
    @After
    fun close(){
        scenario.close()
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeNull() {
        assertNotNull(adapter)
    }

    @Test
    fun adapterSize_ShouldBe2(){
        assertEquals(adapter.count, 2)
    }

    @Test
    fun getView_ShouldReturnCorrectElement(){
    assertEquals(adapter.getView(0),adapter.list[0])
    }



}