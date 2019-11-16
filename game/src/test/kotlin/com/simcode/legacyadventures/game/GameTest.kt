package com.simcode.legacyadventures.game

import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.GameContext
import com.simcode.legacyadventures.game.contexts.GameContextInitializer
import com.simcode.legacyadventures.game.events.ContextChangeEvent
import com.simcode.legacyadventures.game.events.GameWasStarted
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    internal fun `should get exception when getting description from not initialized game`() {
        //given
        val game = Game(emptyList())

        //then
        assertThrows(IllegalArgumentException::class.java) {
            //when
            game.description()
        }
    }

    @Test
    internal fun `should get exception when getting available actions from not initialized game`() {
        //given
        val game = Game(emptyList())

        //then
        assertThrows(IllegalArgumentException::class.java) {
            //when
            game.availableActions()
        }
    }

    @Test
    internal fun `should get exception when performing action on not initialized game`() {
        //given
        val game = Game(emptyList())

        //then
        assertThrows(IllegalArgumentException::class.java) {
            //when
            game.performAction(SomeAction())
        }
    }

    @Test
    internal fun `should get exception when starting game but no initial context present`() {
        //given
        val game = Game(emptyList())

        //then
        assertThrows(IllegalArgumentException::class.java) {
            //when
            game.start()
        }
    }

    @Test
    internal fun `should switch to initial context after game is started`() {
        //given
        val game = Game(listOf(InitialContextInitializer()))

        //when
        game.start()

        //then
        assertThat(game.description()).isEqualTo("This is initial context")
        assertThat(game.availableActions()).isEqualTo(listOf(SomeAction("first initial action"), SomeAction("second initial action")))
    }

    @Test
    internal fun `should get failure when performing unsupported action`() {
        //given
        val game = Game(listOf(InitialContextInitializer()))
        game.start()

        val unsupportedAction = SomeAction("unsupported action")

        //when
        val actionResult = game.performAction(unsupportedAction)

        //then
        assertThat(actionResult).isEqualTo(Failure("Unsupported action: $unsupportedAction"))
    }

    @Test
    internal fun `should get success when performing available action`() {
        //given
        val game = Game(listOf(InitialContextInitializer()))
        game.start()

        //when
        val actionResult = game.performAction(SomeAction("first initial action"))

        //then
        assertThat(actionResult).isEqualTo(Success)
    }

    private data class SomeAction(private val type: String = "some type",
                                  private val targetLabels: List<String> = emptyList()): Action {

        override fun type() = type

        override fun targetLabels() = targetLabels

    }

    private class InitialContext: GameContext {

        override fun description() = "This is initial context"

        override fun availableActions() = listOf(SomeAction("first initial action"), SomeAction("second initial action"))

        override fun performAction(action: Action): ContextActionResult {
            return when {
                !availableActions().contains(action) -> UnsupportedAction
                else -> ContextShouldStay
            }
        }

    }

    private class InitialContextInitializer: GameContextInitializer {

        override fun initialize() = InitialContext()

        override fun supportsEvent(event: ContextChangeEvent) = event is GameWasStarted

    }

}