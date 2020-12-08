package com.simcode.legacyadventures.adventure.parser

import com.simcode.legacyadventures.adventure.Location
import java.nio.file.Path

private const val PASSAGES_DIRECTORY_NAME = "passages"

internal class LocationParser(private val descriptionTextReader: DescriptionTextReader, private val passagesParser: PassagesParser): DirectoryParser<Location> {

    override fun parse(directory: Path): Location {
        val descriptionText = descriptionTextReader.readDescriptionText(directory)

        val passagesDirectory = directory.resolve(PASSAGES_DIRECTORY_NAME)
        val passages = passagesParser.parse(passagesDirectory)

        return Location(descriptionText, passages)
    }


}