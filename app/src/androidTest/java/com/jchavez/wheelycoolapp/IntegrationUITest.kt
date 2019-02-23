package com.jchavez.wheelycoolapp

import android.content.Context
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.jchavez.wheelycoolapp.activities.MainActivity
import android.support.test.InstrumentationRegistry
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


@RunWith(AndroidJUnit4::class)
@LargeTest
class IntegrationUITest {

    private lateinit var stringsToType: ArrayList<String>
    private var instrumentationContext: Context? = null

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getTargetContext()
    }

    @Before
    fun initValidStrings() {
        stringsToType = arrayListOf(
                "One",
                "Two",
                "Three",
                "Four",
                "Five",
                "Six",
                "Seven",
                "Eight",
                "Nine",
                "Ten")
    }

    @Before
    fun cleanDatabase() {
        instrumentationContext?.deleteDatabase(instrumentationContext?.getString(R.string.database_name))
    }

    @Test
    fun checkIntegration() {
        checkListElementsAreDisplayed()
        addWheelOptionsToList()
        moveToWheelFragment()
        testWheelFragmentButton()
        navigateBack()
        deleteAllEntries()
    }

    private fun checkListElementsAreDisplayed() {
        onView(withId(R.id.list))
                .check(matches(isDisplayed()))
        onView(withId(R.id.actionButton))
                .check(matches(isDisplayed()))
        onView(withId(R.id.add))
                .check(matches(isDisplayed()))
    }

    private fun addWheelOptionsToList() {
        stringsToType.forEachIndexed { _, string ->
            onView(withId(R.id.add))
                    .perform(click())
            onView(withId(R.id.editText))
                    .perform(typeText(string))
            onView(withId(R.id.actionButton))
                    .perform(click())
        }
    }

    private fun moveToWheelFragment() {
        onView(withId(R.id.actionButton))
                .perform(click())
    }

    private fun testWheelFragmentButton() {
        onView(withId(R.id.actionButton))
                .perform(click())
    }

    private fun navigateBack() {
        Espresso.pressBack()
    }

    private fun deleteAllEntries() {
        repeat(stringsToType.size) {
            onView(withIndex(withId(R.id.deleteButton), 0)).perform(click())
        }
    }

    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}
