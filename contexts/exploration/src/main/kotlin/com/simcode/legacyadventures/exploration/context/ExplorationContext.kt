package com.simcode.legacyadventures.exploration.context

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.exploration.Exploration
import com.simcode.legacyadventures.exploration.LocationChanged
import com.simcode.legacyadventures.exploration.actions.LeaveAdventure
import com.simcode.legacyadventures.exploration.actions.MoveTo
import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.GameContext

internal class ExplorationContext(private val adventure: Adventure): GameContext {

    private val exploration = Exploration(adventure)

    override fun description() = exploration.currentLocationDescription()

    override fun availableActions(): List<Action> = exploration.availableActions() + LeaveAdventure

    override fun performAction(action: Action): ContextActionResult {
        return when(action) {
            is MoveTo -> performMoveToAction(action)
            is LeaveAdventure -> ContextCanBeClosed
            else -> UnsupportedAction
        }
    }

    private fun performMoveToAction(action: MoveTo): ContextActionResult {
        return when(exploration.moveTo(action.passage)) {
            LocationChanged -> ContextShouldStay
            else -> UnsupportedAction
        }
    }

}