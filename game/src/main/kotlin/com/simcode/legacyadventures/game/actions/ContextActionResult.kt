package com.simcode.legacyadventures.game.actions

import com.simcode.legacyadventures.events.ContextChangeEvent

interface ContextActionResult

object ContextShouldStay: ContextActionResult
object ContextCanBeClosed: ContextActionResult
data class ChangeContext(val contextChangeEvent: ContextChangeEvent): ContextActionResult
object UnsupportedAction: ContextActionResult