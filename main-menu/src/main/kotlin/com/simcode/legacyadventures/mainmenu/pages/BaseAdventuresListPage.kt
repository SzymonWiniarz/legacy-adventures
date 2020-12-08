package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.mainmenu.Exit
import com.simcode.legacyadventures.mainmenu.GoBackToPreviousPage
import com.simcode.legacyadventures.mainmenu.MenuItemSelectionResult
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction
import com.simcode.legacyadventures.mainmenu.actions.SelectGoBackToPreviousScreenOption

internal abstract class BaseAdventuresListPage(descriptionHeader: String, adventures: List<String>, createMenuItemForAdventure: (menuItemOrdinalNumber: Int, adventure: String) -> BaseMenuItemAction) : BaseMainMenuPage(
    descriptionHeader,
    prepareMenuItems(adventures, createMenuItemForAdventure)
)

private fun prepareMenuItems(adventures: List<String>, createMenuItemForAdventure: (menuItemOrdinalNumber: Int, adventure: String) -> BaseMenuItemAction): Map<BaseMenuItemAction, MenuItemSelectionResult> {
    var menuItemOrdinalNumber = 1

    return adventures
        .map { createMenuItemForAdventure.invoke(menuItemOrdinalNumber++, it) to Exit }
        .plus(SelectGoBackToPreviousScreenOption(menuItemOrdinalNumber) to GoBackToPreviousPage)
        .toMap()
}