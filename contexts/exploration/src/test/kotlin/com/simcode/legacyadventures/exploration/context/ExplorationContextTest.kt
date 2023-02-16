package com.simcode.legacyadventures.exploration.context

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.adventure.loader.AdventuresLoader
import com.simcode.legacyadventures.exploration.actions.LeaveAdventure
import com.simcode.legacyadventures.exploration.actions.MoveTo
import com.simcode.legacyadventures.game.actions.ContextCanBeClosed
import com.simcode.legacyadventures.game.actions.ContextShouldStay
import com.simcode.legacyadventures.game.actions.UnsupportedAction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExplorationContextTest {

    private lateinit var adventure: Adventure
    private lateinit var explorationContext: ExplorationContext

    @BeforeEach
    fun setUp() {
        adventure = AdventuresLoader.loadNewAdventure("sample_adventure")
        explorationContext = ExplorationContext(adventure)
    }

    @Test
    fun `should return MoveTo actions to all adjacent locations as available actions`() {
        //when
        val availableActions = explorationContext.availableActions()

        //then
        assertThat(availableActions).containsExactlyInAnyOrder(
            MoveTo("left door"),
            MoveTo("right door"),
            LeaveAdventure
        )
    }

    @Test
    fun `should return description of current location`() {
        //given
        val currentLocation = adventure.staringLocation

        //when
        val description = explorationContext.description()

        //then
        assertThat(description).isEqualTo("${currentLocation.description}\n${currentLocation.passages.description}")
    }

    @Test
    fun `should stay in current location when MoveTo action performed and passage doesn't exist`() {
        //given
        val currentLocation = adventure.findLocation("starting_hall")!!

        //when
        val actionResult = explorationContext.performAction(MoveTo("some not existing passage"))
        val availableActions = explorationContext.availableActions()
        val description = explorationContext.description()

        //then
        assertThat(actionResult).isEqualTo(UnsupportedAction)

        //and
        assertThat(description).isEqualTo("${currentLocation.description}\n${currentLocation.passages.description}")
        assertThat(availableActions).containsExactlyInAnyOrder(
            MoveTo("left door"),
            MoveTo("right door"),
            LeaveAdventure
        )
    }

    @Test
    fun `should move to new location when MoveTo action performed and passage exists`() {
        //given
        val targetLocation = adventure.findLocation("corridor_left")!!

        //when
        val actionResult = explorationContext.performAction(MoveTo("left door"))
        val availableActions = explorationContext.availableActions()
        val description = explorationContext.description()

        //then
        assertThat(actionResult).isEqualTo(ContextShouldStay)

        //and
        assertThat(description).isEqualTo("${targetLocation.description}\n${targetLocation.passages.description}")
        assertThat(availableActions).containsExactlyInAnyOrder(
            MoveTo("back to hall"),
            LeaveAdventure
        )
    }

    @Test
    fun `should back to previous context when LeaveAdventure action performed`() {
        //when
        val actionResult = explorationContext.performAction(LeaveAdventure)

        //then
        assertThat(actionResult).isEqualTo(ContextCanBeClosed)
    }
}