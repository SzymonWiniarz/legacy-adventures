package com.simcode.legacyadventures.adventure

import com.simcode.legacyadventures.adventure.parser.AdventureParser
import java.nio.file.Path

data class Adventure(val description: String, val locations: Map<LocationId, Location>) {

    companion object {

      fun from(directory: Path): Adventure {
          val adventureParser = AdventureParser.create()

          return adventureParser.parse(directory)
      }

    }
}
