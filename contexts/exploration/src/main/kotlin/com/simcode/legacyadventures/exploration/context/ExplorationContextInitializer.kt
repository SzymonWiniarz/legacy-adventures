package com.simcode.legacyadventures.exploration.context

import com.simcode.legacyadventures.adventure.Adventure
import com.simcode.legacyadventures.adventure.loader.AdventuresLoader
import com.simcode.legacyadventures.events.ContextChangeEvent
import com.simcode.legacyadventures.events.NewAdventureStarted
import com.simcode.legacyadventures.events.PendingAdventureStarted
import com.simcode.legacyadventures.game.contexts.GameContext
import com.simcode.legacyadventures.game.contexts.GameContextInitializer

class ExplorationContextInitializer private constructor(): GameContextInitializer {
    override fun initialize(event: ContextChangeEvent): GameContext {
        val adventure = loadAdventure(event)

        return ExplorationContext(adventure)
    }

    private fun loadAdventure(event: ContextChangeEvent): Adventure {
        return when (event) {
            is NewAdventureStarted -> AdventuresLoader.loadNewAdventure(event.adventureName)
            is PendingAdventureStarted -> AdventuresLoader.loadNewAdventure(event.adventureName) //TODO when loading existing adventures is implemented, then use separate method to load pending adventure
            else -> throw IllegalArgumentException("${ExplorationContext::class} cannot be initialized with event $event")
        }
    }

    override fun supportsEvent(event: ContextChangeEvent) = event is NewAdventureStarted || event is PendingAdventureStarted

    companion object {
        fun build() = ExplorationContextInitializer()
    }

}