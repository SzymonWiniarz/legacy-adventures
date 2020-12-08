package com.simcode.legacyadventures.mainmenu

internal interface MainMenuPagesFactory {

    fun initialPage(): MainMenuPage

    fun creditsPage(): MainMenuPage

    fun newAdventurePage(): MainMenuPage

    fun continueAdventurePage(): MainMenuPage
}