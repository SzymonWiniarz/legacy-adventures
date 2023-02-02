package com.simcode.legacyadventures.mainmenu

import com.simcode.legacyadventures.events.ContextChangeEvent

internal interface MenuItemSelectionResult

internal data class GoToNewPage(val menuPage: MainMenuPage) : MenuItemSelectionResult
internal object GoBackToPreviousPage : MenuItemSelectionResult
internal object Exit : MenuItemSelectionResult
internal data class StartNewContext(val newContext: ContextChangeEvent): MenuItemSelectionResult
internal object UnknownItem : MenuItemSelectionResult