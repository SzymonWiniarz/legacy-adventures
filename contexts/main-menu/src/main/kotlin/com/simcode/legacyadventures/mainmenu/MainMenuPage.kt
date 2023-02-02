package com.simcode.legacyadventures.mainmenu

import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal interface MainMenuPage {

    fun description(): String

    fun menuItems(): List<BaseMenuItemAction>

    fun selectMenuItem(menuItem: BaseMenuItemAction): MenuItemSelectionResult

}