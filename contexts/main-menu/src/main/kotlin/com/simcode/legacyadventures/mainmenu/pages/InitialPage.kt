package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.mainmenu.Exit
import com.simcode.legacyadventures.mainmenu.GoToNewPage
import com.simcode.legacyadventures.mainmenu.MainMenuPagesFactory
import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction

internal class InitialPage(pagesFactory: MainMenuPagesFactory) : BaseMainMenuPage(
    "Welcome to Legacy Adventures!",
    linkedMapOf(
        SelectStartNewAdventureOption(1) to GoToNewPage(pagesFactory.newAdventurePage()),
        SelectContinueAdventureOption(2) to GoToNewPage(pagesFactory.continueAdventurePage()),
        SelectCreditsOption(3) to GoToNewPage(pagesFactory.creditsPage()),
        SelectExitOption(4) to Exit
    )

)

internal data class SelectStartNewAdventureOption(val itemOrdinalNumber: Int) : BaseMenuItemAction(itemOrdinalNumber, "Start new Adventure")

internal data class SelectContinueAdventureOption(val itemOrdinalNumber: Int) : BaseMenuItemAction(itemOrdinalNumber, "Continue Adventure")

internal data class SelectCreditsOption(val itemOrdinalNumber: Int) : BaseMenuItemAction(itemOrdinalNumber, "Credits")

internal data class SelectExitOption(val itemOrdinalNumber: Int) : BaseMenuItemAction(itemOrdinalNumber, "Exit")