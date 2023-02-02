package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.mainmenu.MainMenuPage
import com.simcode.legacyadventures.mainmenu.MenuItemSelectionResult
import com.simcode.legacyadventures.mainmenu.UnknownItem
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal abstract class BaseMainMenuPage(private val descriptionHeader: String,
    private val menuItemsToActionsMapping : Map<BaseMenuItemAction, MenuItemSelectionResult>) :
    MainMenuPage {

    private val menuItems = menuItemsToActionsMapping.keys.toList()

    override fun description(): String {
        val descriptionBuilder = StringBuilder(descriptionHeader).append("\n\n");

        menuItems.forEach {
            descriptionBuilder
                .append("[")
                .append(it.menuItemOrdinalNumber)
                .append("] ")
                .append(it.label)
                .append("\n")
        }

        return descriptionBuilder.toString()
    }

    override fun menuItems() = menuItems

    override fun selectMenuItem(menuItem: BaseMenuItemAction): MenuItemSelectionResult {
        return menuItemsToActionsMapping[menuItem] ?: UnknownItem
    }

}