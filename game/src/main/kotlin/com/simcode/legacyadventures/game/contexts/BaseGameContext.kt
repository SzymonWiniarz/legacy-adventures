package com.simcode.legacyadventures.game.contexts

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ContextActionResult
import com.simcode.legacyadventures.game.actions.UnsupportedAction

abstract class BaseGameContext: GameContext {

    override fun performAction(action: Action): ContextActionResult {
        return when {
            !availableActions().contains(action) -> UnsupportedAction
            else -> performSupportedAction(action)
        }
    }

    abstract fun performSupportedAction(action: Action): ContextActionResult

}