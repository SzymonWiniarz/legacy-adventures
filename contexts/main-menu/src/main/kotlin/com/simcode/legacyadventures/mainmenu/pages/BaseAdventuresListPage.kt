package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.events.ContextChangeEvent
import com.simcode.legacyadventures.mainmenu.GoBackToPreviousPage
import com.simcode.legacyadventures.mainmenu.MenuItemSelectionResult
import com.simcode.legacyadventures.mainmenu.StartNewContext
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction
import com.simcode.legacyadventures.mainmenu.actions.SelectGoBackToPreviousScreenOption

internal abstract class BaseAdventuresListPage(
    descriptionHeader: String,
    adventures: List<String>,
    createMenuItemForAdventure: (menuItemOrdinalNumber: Int, adventure: String) -> BaseMenuItemAction,
    newContextForAdventure: (adventure: String) -> ContextChangeEvent
) : BaseMainMenuPage(
    descriptionHeader,
    prepareMenuItems(adventures, createMenuItemForAdventure, newContextForAdventure)
)

private fun prepareMenuItems(
    adventures: List<String>,
    createMenuItemForAdventure: (menuItemOrdinalNumber: Int, adventure: String) -> BaseMenuItemAction,
    newContextForAdventure: (adventure: String) -> ContextChangeEvent
): Map<BaseMenuItemAction, MenuItemSelectionResult> {
    var menuItemOrdinalNumber = 1

    return adventures
        .map { createMenuItemForAdventure.invoke(menuItemOrdinalNumber++, it) to StartNewContext(newContextForAdventure.invoke(it)) }
        .plus(SelectGoBackToPreviousScreenOption(menuItemOrdinalNumber) to GoBackToPreviousPage)
        .toMap()
}