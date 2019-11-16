package com.simcode.legacyadventures

import com.simcode.legacyadventures.adventures.Adventure
import com.simcode.legacyadventures.game.Game
import com.simcode.legacyadventures.ui.cli.UI
import java.nio.file.Paths

fun main() {

    val adventureDirectory = Paths.get(Adventure::class.java.getResource("/adventures/sample_adventure").toURI())
    val adventure = Adventure.from(adventureDirectory)

    val game = Game(emptyList())

    val ui = UI(game)

    ui.startListeningForCommands()

}