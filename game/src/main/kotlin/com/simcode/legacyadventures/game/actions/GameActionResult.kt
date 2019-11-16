package com.simcode.legacyadventures.game.actions

interface GameActionResult

object Success: GameActionResult
data class Failure(val description: String): GameActionResult