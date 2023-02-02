package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.GameContext
import com.simcode.legacyadventures.mainmenu.Exit
import com.simcode.legacyadventures.mainmenu.MainMenu
import com.simcode.legacyadventures.mainmenu.StartNewContext
import com.simcode.legacyadventures.mainmenu.UnknownItem
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal class MainMenuContext(availableAdventures: List<String>, pendingAdventures: List<String>) : GameContext {

    private val mainMenu = MainMenu(availableAdventures, pendingAdventures)

    override fun description() = mainMenu.description()

    override fun availableActions(): List<Action> = mainMenu.menuItems()

    override fun performAction(action: Action): ContextActionResult {
        return when(action) {
            is BaseMenuItemAction -> performMenuItemSelection(action)
            else -> UnsupportedAction
        }
    }

    private fun performMenuItemSelection(menuItem: BaseMenuItemAction): ContextActionResult {
        return when (val selectMenuItemResult = mainMenu.selectMenuItem(menuItem)) {
            is Exit -> ContextCanBeClosed
            is StartNewContext -> ChangeContext(selectMenuItemResult.newContext)
            is UnknownItem -> UnsupportedAction
            else -> ContextShouldStay
        }
    }
}