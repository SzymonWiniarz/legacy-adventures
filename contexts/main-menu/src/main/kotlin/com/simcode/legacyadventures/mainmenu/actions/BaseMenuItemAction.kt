package com.simcode.legacyadventures.mainmenu.actions

import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.ActionCommand

internal abstract class BaseMenuItemAction(val menuItemOrdinalNumber: Int, val label: String) : Action {

    override fun triggeringCommands() = setOf(ActionCommand(menuItemOrdinalNumber.toString()))

}