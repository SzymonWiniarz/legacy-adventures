package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ContextShouldStay
import com.simcode.legacyadventures.mainmenu.actions.SelectGoBackToPreviousScreenOption
import com.simcode.legacyadventures.mainmenu.pages.SelectAdventureToContinue
import com.simcode.legacyadventures.mainmenu.pages.SelectContinueAdventureOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainMenuContextContinueAdventureTest {

    private lateinit var mainMenuContext: MainMenuContext

    private lateinit var previousPageDescription: String

    private lateinit var previousPageActions: List<Action>

    @BeforeEach
    internal fun setup() {
        mainMenuContext = MainMenuContext(
            emptyList(),
            listOf("Adventure started long time ago", "I liked this adventure a lot", "And this was my favourite")
        )

        // given
        previousPageDescription = mainMenuContext.description()
        previousPageActions = mainMenuContext.availableActions()

        mainMenuContext.performAction(SelectContinueAdventureOption(2))
    }

    @Test
    internal fun `should return continue adventure description when on "Continue Adventure" page`() {
        // when
        val description = mainMenuContext.description()

        // then
        assertThat(description).isEqualTo("""
            Choose an Adventure you'd like to continue
            
            [1] Adventure started long time ago
            [2] I liked this adventure a lot
            [3] And this was my favourite
            [4] Go Back
            
        """.trimIndent())
    }

    @Test
    internal fun `should return continue adventure actions when on "Continue Adventure" page`() {
        //when
        val availableActions = mainMenuContext.availableActions()

        // then
        assertThat(availableActions).containsExactly(
            SelectAdventureToContinue(1, "Adventure started long time ago"),
            SelectAdventureToContinue(2, "I liked this adventure a lot"),
            SelectAdventureToContinue(3, "And this was my favourite"),
            SelectGoBackToPreviousScreenOption(4)
        )
    }

    @Test
    internal fun `should return to initial page and stay in main menu context when "Go Back" option chosen`() {
        //when
        val actionResult = mainMenuContext.performAction(SelectGoBackToPreviousScreenOption(4))

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)
        assertThat(mainMenuContext.description()).isEqualTo(previousPageDescription)
        assertThat(mainMenuContext.availableActions()).isEqualTo(previousPageActions)
    }

}