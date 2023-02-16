package com.simcode.legacyadventures.exploration.actions

import com.simcode.legacyadventures.adventure.PassageLabel
import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ActionCommand

internal data class MoveTo(val passage: PassageLabel): Action {

    override fun triggeringCommands() = setOf(ActionCommand("move to $passage"))

}