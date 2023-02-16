package com.simcode.legacyadventures.exploration

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.adventure.PassageLabel
import com.simcode.legacyadventures.exploration.actions.MoveTo

internal class Exploration(private val adventure: Adventure) {

    private var currentLocation = adventure.staringLocation

    fun currentLocationDescription() = "${currentLocation.description}\n${currentLocation.passages.description}"

    fun availableActions() = currentLocation.passages.getAll()
        .map { MoveTo(it.label) }

    fun moveTo(passageLabel: PassageLabel): MoveToResult {
        val passage = currentLocation.passages.findByLabel(passageLabel) ?: return NoSuchPassage
        val targetLocation = adventure.findLocation(passage.targetLocationId)

        requireNotNull(targetLocation) { "Passage $passage points to not existing location" }

        currentLocation = targetLocation

        return LocationChanged
    }

}