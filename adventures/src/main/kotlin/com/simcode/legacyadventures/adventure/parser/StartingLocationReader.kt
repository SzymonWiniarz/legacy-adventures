package com.simcode.legacyadventures.adventure.parser

import com.simcode.legacyadventures.adventure.parser.exceptions.AdventuresParsingException
import java.nio.file.Path

private const val STARTING_LOCATION_FILE_NAME = "starting_location.txt"

internal class StartingLocationReader {

    fun readStartingLocation(directory: Path): String {
        val startingLocationFile = directory.resolve(STARTING_LOCATION_FILE_NAME).toFile()

        if (!startingLocationFile.exists()) {
            throw AdventuresParsingException("No $STARTING_LOCATION_FILE_NAME in $directory")
        }

        return startingLocationFile.readText()
    }

}