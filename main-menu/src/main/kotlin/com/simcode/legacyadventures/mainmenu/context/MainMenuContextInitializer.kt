package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.game.contexts.GameContextInitializer
import com.simcode.legacyadventures.game.events.ContextChangeEvent
import com.simcode.legacyadventures.game.events.GameStarted

class MainMenuContextInitializer private constructor(private val availableAdventures: List<String>, private val pendingAdventures: List<String>) : GameContextInitializer {

    override fun initialize() = MainMenuContext(availableAdventures, pendingAdventures)

    override fun supportsEvent(event: ContextChangeEvent) = event is GameStarted

    companion object {

        fun configure() = Builder()

        fun build() = Builder().build()

    }

    class Builder {

        private var availableAdventures: List<String> = emptyList()

        private var pendingAdventures: List<String> = emptyList()

        fun withAvailableAdventures(availableAdventures: List<String>): Builder {
            this.availableAdventures = availableAdventures
            return this
        }

        fun withPendingAdventures(pendingAdventures: List<String>): Builder {
            this.pendingAdventures = pendingAdventures
            return this
        }

        fun build() = MainMenuContextInitializer(availableAdventures, pendingAdventures)

    }
}