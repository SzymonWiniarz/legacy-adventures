package com.simcode.legacyadventures.mainmenu

internal interface MenuItemSelectionResult

internal data class GoToNewPage(val menuPage: MainMenuPage) : MenuItemSelectionResult
internal object GoBackToPreviousPage : MenuItemSelectionResult
internal object Exit : MenuItemSelectionResult
internal object UnknownItem : MenuItemSelectionResult