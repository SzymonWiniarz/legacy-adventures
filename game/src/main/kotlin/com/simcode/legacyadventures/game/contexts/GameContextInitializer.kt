package com.simcode.legacyadventures.game.contexts

import com.simcode.legacyadventures.events.ContextChangeEvent

interface GameContextInitializer {

    fun initialize(event: ContextChangeEvent): GameContext

    fun supportsEvent(event: ContextChangeEvent): Boolean

}