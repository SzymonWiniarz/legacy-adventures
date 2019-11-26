package com.simcode.legacyadventures.game

import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.GameContext
import com.simcode.legacyadventures.game.contexts.GameContextInitializer
import com.simcode.legacyadventures.game.events.ContextChangeEvent
import com.simcode.legacyadventures.game.events.GameStarted
import java.util.*

class Game private constructor(private val contextInitializers: List<GameContextInitializer>) {

    private val contextStack = Stack<GameContext>()

    private fun start() {
        initializeNewContext(GameStarted)
    }

    fun description() = currentContext().description()

    fun availableActions() = currentContext().availableActions()

    fun performAction(action: Action): GameActionResult {
        when(val actionResult = currentContext().performAction(action)) {
            is ContextCanBeClosed -> contextStack.pop()
            is NewContextShouldBeStarted -> initializeNewContext(actionResult.contextChangeEvent)
            is UnsupportedAction -> return Failure("Unsupported action: $action")
        }

        return Success
    }

    private fun initializeNewContext(contextChangeEvent: ContextChangeEvent) {
        val newGameContext = contextInitializers
            .find { it.supportsEvent(contextChangeEvent) }
            ?.initialize()

        requireNotNull(newGameContext) { "No matching game context that supports the event: $contextChangeEvent" }

        contextStack.push(newGameContext)
    }

    private fun currentContext(): GameContext {
        require(contextStack.isNotEmpty()) { "Game is not in any context right now" }

        return contextStack.peek()
    }


    companion object {
        fun configure() = Builder()

        fun start() = Builder().start()
    }

    class Builder {
        private var contextInitializers: List<GameContextInitializer> = emptyList()

        fun withContextInitializers(contextInitializers: List<GameContextInitializer>): Builder {
            this.contextInitializers = contextInitializers
            return this
        }

        fun start(): Game {
            val game = Game(contextInitializers)
            game.start()

            return game
        }
    }
}