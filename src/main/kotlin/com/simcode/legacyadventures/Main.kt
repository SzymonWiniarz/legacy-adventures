package com.simcode.legacyadventures

import com.simcode.legacyadventures.adventures.Adventure
import com.simcode.legacyadventures.adventures.parser.AdventureParser
import com.simcode.legacyadventures.game.Game
import com.simcode.legacyadventures.ui.UI
import java.nio.file.Paths

fun main() {

    val adventureDirectory = Paths.get(Adventure::class.java.getResource("/adventures/sample_adventure").toURI())
    val adventure = AdventureParser.create().parse(adventureDirectory)

    val game = Game(adventure, "starting_hall")

    val ui = UI(game)

    ui.startListeningForCommands()

}