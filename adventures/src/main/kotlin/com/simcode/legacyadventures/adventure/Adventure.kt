package com.simcode.legacyadventures.adventure

import com.simcode.legacyadventures.adventure.parser.AdventureParser
import java.nio.file.Path

data class Adventure(val startingLocationId: LocationId, val description: String, private val locations: Map<LocationId, Location>) {

    val staringLocation = locations[startingLocationId] ?: throw IllegalArgumentException("Adventure does not have staring location: $startingLocationId")

    fun findLocation(locationId: LocationId) = locations[locationId]

    fun getAllLocations() = locations

    companion object {

      fun from(directory: Path): Adventure {
          val adventureParser = AdventureParser.create()

          return adventureParser.parse(directory)
      }

    }
}
