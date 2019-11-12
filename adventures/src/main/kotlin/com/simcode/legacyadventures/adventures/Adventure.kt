package com.simcode.legacyadventures.adventures

data class Adventure(val description: String, val locations: Map<LocationId, Location>)
