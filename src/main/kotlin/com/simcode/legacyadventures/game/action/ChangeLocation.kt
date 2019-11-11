package com.simcode.legacyadventures.game.action

import com.simcode.legacyadventures.adventures.LocationId

data class ChangeLocation(val passageLabel: String, val targetLocationId: LocationId): Action