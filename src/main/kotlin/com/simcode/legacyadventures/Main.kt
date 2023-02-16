package com.simcode.legacyadventures

import com.simcode.legacyadventures.exploration.context.ExplorationContextInitializer
import com.simcode.legacyadventures.game.Game
import com.simcode.legacyadventures.mainmenu.context.MainMenuContextInitializer
import com.simcode.legacyadventures.ui.cli.UI

fun main() {

    val game = Game
        .configure()
        .withContextInitializers(listOf(
            MainMenuContextInitializer
                .configure()
                .withAvailableAdventures(listOf("sample_adventure"))
                .build(),

            ExplorationContextInitializer.build()
        ))
        .start()

    val ui = UI(game)

    ui.startListeningForCommands()
}