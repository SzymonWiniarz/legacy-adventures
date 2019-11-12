package com.simcode.legacyadventures.adventures.parser

import com.simcode.legacyadventures.adventures.parser.exceptions.AdventuresParsingException
import java.nio.file.Path

private const val DESCRIPTION_FILE_NAME = "description.txt"

class DescriptionTextReader {

    fun readDescriptionText(directory: Path): String {
        val descriptionFile = directory.resolve(DESCRIPTION_FILE_NAME).toFile()

        if (!descriptionFile.exists()) {
            throw AdventuresParsingException("No $DESCRIPTION_FILE_NAME in $directory")
        }

        return descriptionFile.readText()
    }

}