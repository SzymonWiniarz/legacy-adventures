package com.simcode.legacyadventures.game

import com.simcode.legacyadventures.adventures.Adventure
import com.simcode.legacyadventures.adventures.LocationId
import com.simcode.legacyadventures.game.action.Action
import com.simcode.legacyadventures.game.action.ChangeLocation

class Game(private val adventure: Adventure, private var currentLocationId: LocationId) {

    fun getDescription(): String {
        val currentLocation = adventure.locations[currentLocationId]

        return "${currentLocation?.description}\n${currentLocation?.passages?.description}"
    }

    fun getAvailableActions(): List<Action> {
        val currentLocation = adventure.locations[currentLocationId]

        return currentLocation?.passages?.passages?.map { ChangeLocation(it.label, it.targetLocationId) } ?: emptyList()
    }

    fun switchLocation(targetLocationId: LocationId) {
        val currentLocation = adventure.locations[currentLocationId] ?: throw IllegalStateException("Location '$currentLocationId' doesn't exist")
        val availablePassages = currentLocation.passages.passages
        val targetLocationReachable = availablePassages
            .map { it.targetLocationId }
            .contains(targetLocationId)

        require(targetLocationReachable) { "You cannot pass from $currentLocationId to $targetLocationId" }

        this.currentLocationId = targetLocationId
    }

}