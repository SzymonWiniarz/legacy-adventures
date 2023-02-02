package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.events.NewAdventureStarted
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal class NewAdventurePage(availableAdventures: List<String>) : BaseAdventuresListPage(
    "Choose an Adventure you'd like to begin",
    availableAdventures,
    { itemOrdinalNumber, adventureName -> SelectNewAdventureToStart(itemOrdinalNumber, adventureName) },
    { adventureName -> NewAdventureStarted(adventureName) }
)

internal data class SelectNewAdventureToStart(val itemOrdinalNumber: Int, val adventureName: String) : BaseMenuItemAction(itemOrdinalNumber, adventureName)