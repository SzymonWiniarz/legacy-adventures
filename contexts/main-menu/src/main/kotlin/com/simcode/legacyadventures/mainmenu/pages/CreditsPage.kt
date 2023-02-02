package com.simcode.legacyadventures.mainmenu.pages

import com.simcode.legacyadventures.mainmenu.GoBackToPreviousPage
import com.simcode.legacyadventures.mainmenu.actions.SelectGoBackToPreviousScreenOption

internal object CreditsPage : BaseMainMenuPage(
    "This game has been created for fun by Szymon Winiarz",
    mapOf(
        SelectGoBackToPreviousScreenOption(1) to GoBackToPreviousPage
    )
)