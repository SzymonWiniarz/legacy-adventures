package com.simcode.legacyadventures

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.exploration.context.ExplorationContextInitializer
import com.simcode.legacyadventures.game.Game
import com.simcode.legacyadventures.mainmenu.context.MainMenuContextInitializer
import com.simcode.legacyadventures.ui.cli.UI
import java.nio.file.Paths

fun main() {

    val adventureDirectory = Paths.get(Adventure::class.java.getResource("/adventures/sample_adventure").toURI())
    val adventure = Adventure.from(adventureDirectory)

    val game = Game
        .configure()
        .withContextInitializers(listOf(
            MainMenuContextInitializer
                .configure()
                .withAvailableAdventures(listOf("sample_adventure"))
                .build()
        ))
        .start()

    val ui = UI(game)

    ui.startListeningForCommands()
}