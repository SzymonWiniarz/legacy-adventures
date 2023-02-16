package com.simcode.legacyadventures.adventure.loader

import com.simcode.legacyadventures.adventure.Adventure
import java.nio.file.Paths

object AdventuresLoader {

    private const val ADVENTURES_DIRECTORY = "/adventures/"

    fun loadNewAdventure(adventureName: String): Adventure {
        val pathToAdventureDirectory = "$ADVENTURES_DIRECTORY$adventureName"
        val adventureDirectory = Paths.get(javaClass.getResource(pathToAdventureDirectory)?.toURI()
            ?: throw IllegalArgumentException("Adventure $adventureName could not be found in adventures directory"))

        return Adventure.from(adventureDirectory)
    }

}