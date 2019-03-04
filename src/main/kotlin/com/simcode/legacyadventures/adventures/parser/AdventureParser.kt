package com.simcode.legacyadventures.adventures.parser

import com.simcode.legacyadventures.adventures.Adventure
import com.simcode.legacyadventures.adventures.Location
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

private const val LOCATIONS_DIRECTORY_NAME = "locations"

class AdventureParser(private val descriptionTextReader: DescriptionTextReader, private val locationParser: LocationParser): DirectoryParser<Adventure> {

    override fun parse(directory: Path): Adventure {
        val descriptionText = descriptionTextReader.readDescriptionText(directory)

        val locationsDirectory = directory.resolve(LOCATIONS_DIRECTORY_NAME)

        val locations: Map<String, Location>  = Files.list(locationsDirectory)
            .filter { Files.isDirectory(it) }
            .collect(Collectors.toMap({ it.fileName.toString() }, { locationParser.parse(it) }))

        return Adventure(descriptionText, locations)
    }

    companion object Builder {

        fun create(): AdventureParser {
            val descriptionTextReader = DescriptionTextReader()
            val passagesParser = PassagesParser(descriptionTextReader)
            val locationParser = LocationParser(descriptionTextReader, passagesParser)

            return AdventureParser(descriptionTextReader, locationParser)
        }

    }
}