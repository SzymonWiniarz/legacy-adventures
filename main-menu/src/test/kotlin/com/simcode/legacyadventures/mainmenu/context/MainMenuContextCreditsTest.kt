package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ContextShouldStay
import com.simcode.legacyadventures.mainmenu.actions.*
import com.simcode.legacyadventures.mainmenu.pages.InitialPage
import com.simcode.legacyadventures.mainmenu.pages.SelectCreditsOption
import com.simcode.legacyadventures.mainmenu.pages.SelectStartNewAdventureOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainMenuContextCreditsTest {

    private lateinit var mainMenuContext: MainMenuContext

    private lateinit var previousPageDescription: String

    private lateinit var previousPageActions: List<Action>

    @BeforeEach
    internal fun setup() {
        mainMenuContext = MainMenuContext(
            emptyList(),
            emptyList()
        )

        // given
        previousPageDescription = mainMenuContext.description()
        previousPageActions = mainMenuContext.availableActions()

        mainMenuContext.performAction(SelectCreditsOption(3))
    }

    @Test
    internal fun `should return credits description when on "Credits" page`() {
        // when
        val description = mainMenuContext.description()

        // then
        assertThat(description).isEqualTo("""
            This game has been created for fun by Szymon Winiarz
            
            [1] Go Back
            
        """.trimIndent())
    }

    @Test
    internal fun `should return credits actions when on "Credits" page`() {
        //when
        val availableActions = mainMenuContext.availableActions()

        // then
        assertThat(availableActions).containsExactly(
            SelectGoBackToPreviousScreenOption(1)
        )
    }

    @Test
    internal fun `should return to initial page and stay in main menu context when "Go Back" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectGoBackToPreviousScreenOption(1))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
        assertThat(mainMenuContext.description()).isEqualTo(previousPageDescription)
        assertThat(mainMenuContext.availableActions()).isEqualTo(previousPageActions)
    }

}