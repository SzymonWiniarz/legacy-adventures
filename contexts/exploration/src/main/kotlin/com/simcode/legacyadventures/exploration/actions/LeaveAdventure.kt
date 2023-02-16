package com.simcode.legacyadventures.exploration.actions

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ActionCommand

object LeaveAdventure: Action {

    override fun triggeringCommands() = setOf(ActionCommand("leave adventure"))

}