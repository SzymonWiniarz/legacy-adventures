package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ChangeContext
import com.simcode.legacyadventures.game.actions.ContextShouldStay
import com.simcode.legacyadventures.events.NewAdventureStarted
import com.simcode.legacyadventures.mainmenu.actions.SelectGoBackToPreviousScreenOption
import com.simcode.legacyadventures.mainmenu.pages.SelectNewAdventureToStart
import com.simcode.legacyadventures.mainmenu.pages.SelectStartNewAdventureOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainMenuContextNewAdventureTest {

    private lateinit var mainMenuContext: MainMenuContext

    private lateinit var previousPageDescription: String

    private lateinit var previousPageActions: List<Action>

    @BeforeEach
    internal fun setup() {
        mainMenuContext = MainMenuContext(
            listOf("Dungeons And Dragons", "Lord of the Rings"),
            emptyList()
        )

        // given
        previousPageDescription = mainMenuContext.description()
        previousPageActions = mainMenuContext.availableActions()

        mainMenuContext.performAction(SelectStartNewAdventureOption(1))
    }

    @Test
    internal fun `should return new adventures description when on 'Start New Adventure' page`() {
        // when
        val description = mainMenuContext.description()

        // then
        assertThat(description).isEqualTo("""
            Choose an Adventure you'd like to begin
            
            [1] Dungeons And Dragons
            [2] Lord of the Rings
            [3] Go Back
            
        """.trimIndent())
    }

    @Test
    internal fun `should return new adventures actions when on 'New Adventures' page`() {
        //when
        val availableActions = mainMenuContext.availableActions()

        // then
        assertThat(availableActions).containsExactly(
            SelectNewAdventureToStart(1, "Dungeons And Dragons"),
            SelectNewAdventureToStart(2, "Lord of the Rings"),
            SelectGoBackToPreviousScreenOption(3)
        )
    }

    @Test
    internal fun `should return to initial page and stay in main menu context when 'Go Back' option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectGoBackToPreviousScreenOption(3))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
        assertThat(mainMenuContext.description()).isEqualTo(previousPageDescription)
        assertThat(mainMenuContext.availableActions()).isEqualTo(previousPageActions)
    }

    @Test
    fun `should start new context when adventure selected`() {
        //given
        val adventureName = "Dungeons And Dragons"

        //when
        val actionResult = mainMenuContext.performAction(SelectNewAdventureToStart(1, adventureName))

        //then
        assertThat(actionResult).isEqualTo(ChangeContext(NewAdventureStarted(adventureName)))
    }
}