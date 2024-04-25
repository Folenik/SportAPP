package com.mosz.sportapp

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.performScrollTo
import com.mosz.sportapp.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val defaultTimeout = 5000L
    private val specifiedTimeout = 30000L

    @Test
    fun app_loads_with_progress_bar_and_disappears() {
        composeTestRule.onNodeWithTag(testTag = "pBar").assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag("pBar"),
            timeoutMillis = defaultTimeout
        )
    }

    @Test
    fun basic_ui_components_are_present() {
        composeTestRule.onNodeWithTag(testTag = "pBar").assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag("pBar"),
            timeoutMillis = defaultTimeout
        )
        runBlocking {
            delay(defaultTimeout)
        }
        composeTestRule.onAllNodesWithContentDescription(label = "img").onFirst().assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(testTag = "title", useUnmergedTree = true).onLast().assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(testTag = "subtitle", useUnmergedTree = true).onLast().assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(testTag = "date", useUnmergedTree = true).onLast().assertIsDisplayed()
        composeTestRule.onAllNodesWithTag(testTag = "menuItem").onFirst().assertIsDisplayed()
    }

    @Test
    fun click_on_schedule_changes_screen() {
        composeTestRule.onNodeWithTag(testTag = "pBar").assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag("pBar"),
            timeoutMillis = defaultTimeout
        )

        runBlocking {
            delay(defaultTimeout)
        }

        val initialTitleText =
            composeTestRule.onAllNodesWithTag(testTag = "title", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text
        val initialSubtitleText =
            composeTestRule.onAllNodesWithTag(testTag = "subtitle", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text


        composeTestRule.onAllNodesWithTag(testTag = "menuItem").onLast().assertIsDisplayed().performClick()

        runBlocking {
            delay(defaultTimeout)
        }

        val newTitleText =
            composeTestRule.onAllNodesWithTag(testTag = "title", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text
        val newSubtitleText =
            composeTestRule.onAllNodesWithTag(testTag = "subtitle", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text

        assertNotEquals(
            "Title text should have changed",
            initialTitleText,
            newTitleText
        )
        assertNotEquals(
            "Subtitle text should have changed",
            initialSubtitleText,
            newSubtitleText
        )
    }

    @Test
    fun schedule_check_if_refreshes_after_specified_delay() {
        composeTestRule.onNodeWithTag(testTag = "pBar").assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag("pBar"),
            timeoutMillis = defaultTimeout
        )

        runBlocking {
            delay(defaultTimeout)
        }

        composeTestRule.onAllNodesWithTag(testTag = "menuItem").onLast().assertIsDisplayed().performClick()

        runBlocking {
            delay(defaultTimeout)
        }

        val initialDateText =
            composeTestRule.onAllNodesWithTag(testTag = "date", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text

        runBlocking {
            delay(specifiedTimeout)
        }

        val newDateText =
            composeTestRule.onAllNodesWithTag(testTag = "date", useUnmergedTree = true).onLast().fetchSemanticsNode().config[Text][0].text

        assertNotEquals(
            "Date should have changed",
            initialDateText,
            newDateText
        )
    }

    @Test
    fun click_on_item_open_video() {
        composeTestRule.onNodeWithTag(testTag = "pBar").assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag("pBar"),
            timeoutMillis = defaultTimeout
        )

        runBlocking {
            delay(defaultTimeout)
        }

        composeTestRule.onAllNodesWithTag(testTag = "title", useUnmergedTree = true).onLast().performClick()

        runBlocking {
            delay(defaultTimeout)
        }

        composeTestRule.onNodeWithTag("videoPlayer").assertIsDisplayed()
    }
}