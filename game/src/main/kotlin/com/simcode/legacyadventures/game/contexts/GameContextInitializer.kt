package com.simcode.legacyadventures.game.contexts

import com.simcode.legacyadventures.game.events.ContextChangeEvent

interface GameContextInitializer {

    fun initialize(): GameContext

    fun supportsEvent(event: ContextChangeEvent): Boolean

}