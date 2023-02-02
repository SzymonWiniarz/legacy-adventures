package com.simcode.legacyadventures.mainmenu

import com.simcode.legacyadventures.mainmenu.actions.BaseMenuItemAction
import com.simcode.legacyadventures.mainmenu.pages.ContinueAdventurePage
import com.simcode.legacyadventures.mainmenu.pages.CreditsPage
import com.simcode.legacyadventures.mainmenu.pages.InitialPage
import com.simcode.legacyadventures.mainmenu.pages.NewAdventurePage
import java.util.*

internal class MainMenu(private val availableAdventures: List<String>, private val pendingAdventures: List<String>) : MainMenuPagesFactory {

    private val menuPages: Stack<MainMenuPage> = Stack()

    init {
        menuPages.push(initialPage())
    }

    fun description() = currentMenuPage().description()

    fun menuItems() = currentMenuPage().menuItems()

    fun selectMenuItem(menuItem: BaseMenuItemAction): MenuItemSelectionResult {
        val selectionResult = currentMenuPage().selectMenuItem(menuItem)

        when (selectionResult) {
            is GoToNewPage -> menuPages.push(selectionResult.menuPage)
            is GoBackToPreviousPage -> if (!menuPages.isEmpty()) menuPages.pop()
        }

        return selectionResult
    }

    private fun currentMenuPage() = menuPages.peek()

    override fun initialPage() = InitialPage(this)

    override fun creditsPage() = CreditsPage

    override fun newAdventurePage() = NewAdventurePage(availableAdventures)

    override fun continueAdventurePage() = ContinueAdventurePage(pendingAdventures)


}