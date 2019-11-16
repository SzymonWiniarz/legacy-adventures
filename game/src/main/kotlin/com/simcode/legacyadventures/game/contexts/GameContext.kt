package com.simcode.legacyadventures.game.contexts

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ContextActionResult

interface GameContext {

    fun description(): String

    fun availableActions(): List<Action>

    fun performAction(action: Action): ContextActionResult

}