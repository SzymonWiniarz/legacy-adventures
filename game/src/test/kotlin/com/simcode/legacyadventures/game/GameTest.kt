package com.simcode.legacyadventures.game

import com.simcode.legacyadventures.events.ContextChangeEvent
import com.simcode.legacyadventures.events.GameStarted
import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.GameContext
import com.simcode.legacyadventures.game.contexts.GameContextInitializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    internal fun `should get exception when starting game but no initial context present`() {
        //expect
        assertThrows(IllegalArgumentException::class.java) {
            //when
            Game.start()
        }
    }

    @Test
    internal fun `should switch to initial context after game is started`() {
        //when
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer))
            .start()

        //then
        assertThat(game.description()).isEqualTo(INITIAL_CONTEXT_INITIAL_DESCRIPTION)
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext, EndGame))
    }

    @Test
    internal fun `should get failure when performing unsupported action`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer))
            .start()

        val unsupportedAction = SomeAction

        //when
        val actionResult = game.performAction(unsupportedAction)

        //then
        assertThat(actionResult).isEqualTo(Failure("Unsupported action: $unsupportedAction"))
    }

    @Test
    internal fun `should get success when performing available action`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer))
            .start()

        //when
        val actionResult = game.performAction(DoSomethingWithinCurrentContext)

        //then
        assertThat(actionResult).isEqualTo(Success)
        assertThat(game.description()).isEqualTo(INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION)
    }

    @Test
    internal fun `should change description and available actions when performing action that changes context`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer, OtherContextInitializer))
            .start()

        //when
        val actionResult = game.performAction(SwitchToDifferentContext)

        //then
        assertThat(actionResult).isEqualTo(Success)
        assertThat(game.description()).isEqualTo(OTHER_CONTEXT_INITIAL_DESCRIPTION)
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, GoBackToInitialContext))
    }

    @Test
    internal fun `should get description and available actions of previous context after performing action that finishes current context`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer, OtherContextInitializer))
            .start()

        game.performAction(SwitchToDifferentContext)

        //when
        val actionResult = game.performAction(GoBackToInitialContext)

        //then
        assertThat(actionResult).isEqualTo(Success)
        assertThat(game.description()).isEqualTo(INITIAL_CONTEXT_INITIAL_DESCRIPTION)
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext, EndGame))
    }

    @Test
    internal fun `should get description and available actions of previous context in it's last state after performing action that finishes current context`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer, OtherContextInitializer))
            .start()

        game.performAction(DoSomethingWithinCurrentContext)
        game.performAction(SwitchToDifferentContext)

        //when
        val actionResult = game.performAction(GoBackToInitialContext)

        //then
        assertThat(actionResult).isEqualTo(Success)
        assertThat(game.description()).isEqualTo(INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION)
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext, EndGame))
    }

    @Test
    internal fun `should get description and available actions of initial state of the next context after performing action that switches to this context again`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer, OtherContextInitializer))
            .start()

        game.performAction(SwitchToDifferentContext)
        game.performAction(DoSomethingWithinCurrentContext)
        game.performAction(GoBackToInitialContext)

        //when
        val actionResult = game.performAction(SwitchToDifferentContext)

        //then
        assertThat(actionResult).isEqualTo(Success)
        assertThat(game.description()).isEqualTo(OTHER_CONTEXT_INITIAL_DESCRIPTION)
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, GoBackToInitialContext))
    }

    @Test
    fun `should get GameOver result after performing action that exist initial context`() {
        //given
        val game = Game.configure()
            .withContextInitializers(listOf(InitialContextInitializer))
            .start()

        //when
        val actionResult = game.performAction(EndGame)

        //then
        assertThat(actionResult).isEqualTo(GameOver)

    }
}

// ----------------------Actions----------------------//

private object SomeAction: Action {

    override fun triggeringCommands() = setOf(ActionCommand("some action"))

}

private object DoSomethingWithinCurrentContext: Action {

    override fun triggeringCommands() = setOf(ActionCommand("do something within the current context"))

}

private object SwitchToDifferentContext: Action {

    override fun triggeringCommands() = setOf(ActionCommand("switch to a different context"))

}

private object GoBackToInitialContext: Action {

    override fun triggeringCommands() = setOf(ActionCommand("go back to initial context"))

}

private object EndGame: Action {

    override fun triggeringCommands() = setOf(ActionCommand("end game"))

}


// ----------------------Events----------------------//

private object EventChangingContextToSubsequent: ContextChangeEvent

// ----------------------Contexts----------------------//

private object InitialContextInitializer: GameContextInitializer {

    override fun initialize(event: ContextChangeEvent) = InitialContext()

    override fun supportsEvent(event: ContextChangeEvent) = event is GameStarted

}

private const val INITIAL_CONTEXT_INITIAL_DESCRIPTION = "Initial context in it's initial state"
private const val INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION = "Initial context in it's subsequent state"

private class InitialContext: GameContext {

    private var description = INITIAL_CONTEXT_INITIAL_DESCRIPTION

    override fun description() = description

    override fun availableActions() = listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext, EndGame)

    override fun performAction(action: Action): ContextActionResult {
        return when (action) {
            SwitchToDifferentContext -> ChangeContext(EventChangingContextToSubsequent)
            DoSomethingWithinCurrentContext -> {
                description = INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION

                ContextShouldStay
            }
            EndGame -> ContextCanBeClosed
            else -> UnsupportedAction
        }
    }

}

private object OtherContextInitializer: GameContextInitializer {

    override fun supportsEvent(event: ContextChangeEvent): Boolean {
        return event == EventChangingContextToSubsequent
    }

    override fun initialize(event: ContextChangeEvent) = OtherContext()

}

private const val OTHER_CONTEXT_INITIAL_DESCRIPTION = "Other context in it's initial state"
private const val OTHER_CONTEXT_SUBSEQUENT_DESCRIPTION = "Other context in it's subsequent state"

private class OtherContext: GameContext {

    private var description = OTHER_CONTEXT_INITIAL_DESCRIPTION

    override fun description() = description

    override fun availableActions() = listOf(DoSomethingWithinCurrentContext, GoBackToInitialContext)

    override fun performAction(action: Action): ContextActionResult {
        return when (action) {
            GoBackToInitialContext -> ContextCanBeClosed
            DoSomethingWithinCurrentContext -> {
                description = OTHER_CONTEXT_SUBSEQUENT_DESCRIPTION

                ContextShouldStay
            }
            else -> UnsupportedAction
        }
    }

}