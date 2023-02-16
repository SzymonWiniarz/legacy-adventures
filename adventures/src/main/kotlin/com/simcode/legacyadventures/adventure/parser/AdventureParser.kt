package com.simcode.legacyadventures.adventure.parser

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.adventure.Location
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

private const val LOCATIONS_DIRECTORY_NAME = "locations"

internal class AdventureParser(
    private val startingLocationReader: StartingLocationReader,
    private val descriptionTextReader: DescriptionTextReader,
    private val locationParser: LocationParser
) : DirectoryParser<Adventure> {

    override fun parse(directory: Path): Adventure {
        val descriptionText = descriptionTextReader.readDescriptionText(directory)
        val startingLocation = startingLocationReader.readStartingLocation(directory)

        val locationsDirectory = directory.resolve(LOCATIONS_DIRECTORY_NAME)

        val locations: Map<String, Location> = Files.list(locationsDirectory)
            .filter { Files.isDirectory(it) }
            .collect(Collectors.toMap({ it.fileName.toString() }, { locationParser.parse(it) }))

        return Adventure(startingLocation, descriptionText, locations)
    }

    companion object {

        fun create(): AdventureParser {
            val descriptionTextReader = DescriptionTextReader()
            val startingLocationReader = StartingLocationReader()
            val passagesParser = PassagesParser(descriptionTextReader)
            val locationParser = LocationParser(descriptionTextReader, passagesParser)

            return AdventureParser(startingLocationReader, descriptionTextReader, locationParser)
        }

    }
}