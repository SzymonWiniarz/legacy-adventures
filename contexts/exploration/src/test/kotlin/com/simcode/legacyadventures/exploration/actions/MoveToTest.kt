package com.simcode.legacyadventures.exploration.actions

import com.simcode.legacyadventures.game.actions.ActionCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoveToTest {

    @Test
    fun `should have proper triggerring commands for MoveTo action`() {
        //given
        val moveToAction = MoveTo("some passage")

        //when
        val triggeringCommands = moveToAction.triggeringCommands()

        //then
        assertThat(triggeringCommands).containsExactlyInAnyOrder(ActionCommand("move to some passage"))
    }

}