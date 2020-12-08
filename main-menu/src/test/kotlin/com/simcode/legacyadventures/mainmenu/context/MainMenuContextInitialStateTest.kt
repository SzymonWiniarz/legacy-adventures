package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.actions.ContextCanBeClosed
import com.simcode.legacyadventures.game.actions.ContextShouldStay
import com.simcode.legacyadventures.mainmenu.pages.SelectContinueAdventureOption
import com.simcode.legacyadventures.mainmenu.pages.SelectCreditsOption
import com.simcode.legacyadventures.mainmenu.pages.SelectExitOption
import com.simcode.legacyadventures.mainmenu.pages.SelectStartNewAdventureOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainMenuContextInitialStateTest {

    private lateinit var mainMenuContext: MainMenuContext

    @BeforeEach
    internal fun setup() {
        mainMenuContext = MainMenuContext(
            emptyList(),
            emptyList()
        )
    }

    @Test
    internal fun `should return initial description when in initial state`() {
        // when
        val description = mainMenuContext.description()

        // then
        assertThat(description).isEqualTo("""
            Welcome to Legacy Adventures!
            
            [1] Start new Adventure
            [2] Continue Adventure
            [3] Credits
            [4] Exit
            
        """.trimIndent())
    }

    @Test
    internal fun `should return initial actions when in initial state`() {
        //when
        val availableActions = mainMenuContext.availableActions()

        // then
        assertThat(availableActions).containsExactly(
            SelectStartNewAdventureOption(1),
            SelectContinueAdventureOption(2),
            SelectCreditsOption(3),
            SelectExitOption(4)
        )
    }

    @Test
    internal fun `should stay in main menu context when "Start new Adventure" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectStartNewAdventureOption(1))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
    }

    @Test
    internal fun `should stay in main menu context when "Continue Adventure" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectContinueAdventureOption(2))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
    }

    @Test
    internal fun `should stay in main menu context when "Credits" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectCreditsOption(3))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
    }

    @Test
    internal fun `should close main menu context when "Exit" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectExitOption(4))

        //then
        assertThat(actionResult).isEqualTo(ContextCanBeClosed)
    }

}