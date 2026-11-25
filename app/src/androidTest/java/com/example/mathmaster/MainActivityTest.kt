
package com.example.mathmaster

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenDisplaysCorrectly() {

        composeTestRule
            .onNodeWithText("MATHMASTER")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Choose Your Activity")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Your Learning Progress")
            .assertIsDisplayed()
    }

    @Test
    fun settingsScreenOpensSuccessfully() {

        composeTestRule
            .onNodeWithContentDescription("Open settings")
            .performClick()

        composeTestRule
            .onNodeWithText("Personalise Your Learning")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Learning Difficulty")
            .assertIsDisplayed()
    }

    @Test
    fun statisticsScreenOpensSuccessfully() {

        composeTestRule
            .onNodeWithText("View Full Statistics")
            .performClick()

        composeTestRule
            .onNodeWithText("My Learning Statistics")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Total Attempts")
            .assertIsDisplayed()
    }

    @Test
    fun settingsScreenReturnsToHome() {

        composeTestRule
            .onNodeWithContentDescription("Open settings")
            .performClick()

        composeTestRule
            .onNodeWithText("Personalise Your Learning")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back")
            .performClick()

        composeTestRule
            .onNodeWithText("MATHMASTER")
            .assertIsDisplayed()
    }

    @Test
    fun statisticsScreenReturnsToHome() {

        composeTestRule
            .onNodeWithText("View Full Statistics")
            .performClick()

        composeTestRule
            .onNodeWithText("My Learning Statistics")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Back")
            .performClick()

        composeTestRule
            .onNodeWithText("MATHMASTER")
            .assertIsDisplayed()
    }
}
