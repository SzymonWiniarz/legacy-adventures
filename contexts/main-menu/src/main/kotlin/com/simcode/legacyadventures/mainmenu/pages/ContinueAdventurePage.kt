package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.events.PendingAdventureStarted
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal class ContinueAdventurePage(pendingAdventures: List<String>) : BaseAdventuresListPage(
    "Choose an Adventure you'd like to continue",
    pendingAdventures,
    { itemOrdinalNumber, adventureName -> SelectAdventureToContinue(itemOrdinalNumber, adventureName) },
    { adventureName -> PendingAdventureStarted(adventureName) }
)

internal data class SelectAdventureToContinue(val itemOrdinalNumber: Int, val adventureName: String) : BaseMenuItemAction(itemOrdinalNumber, adventureName)