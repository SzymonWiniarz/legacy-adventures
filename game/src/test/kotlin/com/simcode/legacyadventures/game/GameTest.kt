package com.simcode.legacyadventures.game

import com.simcode.legacyadventures.game.actions.*
import com.simcode.legacyadventures.game.contexts.BaseGameContext
import com.simcode.legacyadventures.game.contexts.GameContextInitializer
import com.simcode.legacyadventures.game.events.ContextChangeEvent
import com.simcode.legacyadventures.game.events.GameStarted
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
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext))
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
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext))
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
        assertThat(game.availableActions()).isEqualTo(listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext))
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

}

// ----------------------Actions----------------------//

private object SomeAction: Action {

    override fun type() = "some action"

    override fun targetLabels() = emptyList<String>()

}

private object DoSomethingWithinCurrentContext: Action {

    override fun type() = "do something within the current context"

    override fun targetLabels() = emptyList<String>()

}

private object SwitchToDifferentContext: Action {

    override fun type() = "switch to a different context"

    override fun targetLabels() = emptyList<String>()

}

private object GoBackToInitialContext: Action {

    override fun type() = "go back to initial context"

    override fun targetLabels() = emptyList<String>()

}


// ----------------------Events----------------------//

private object EventChangingContextToSubsequent: ContextChangeEvent

// ----------------------Contexts----------------------//

private object InitialContextInitializer: GameContextInitializer {

    override fun initialize() = InitialContext()

    override fun supportsEvent(event: ContextChangeEvent) = event is GameStarted

}

private const val INITIAL_CONTEXT_INITIAL_DESCRIPTION = "Initial context in it's initial state"
private const val INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION = "Initial context in it's subsequent state"

private class InitialContext: BaseGameContext() {

    private var description = INITIAL_CONTEXT_INITIAL_DESCRIPTION

    override fun description() = description

    override fun availableActions() = listOf(DoSomethingWithinCurrentContext, SwitchToDifferentContext)

    override fun performSupportedAction(action: Action): ContextActionResult {
        return when (action) {
            SwitchToDifferentContext -> NewContextShouldBeStarted(EventChangingContextToSubsequent)
            else -> {
                description = INITIAL_CONTEXT_SUBSEQUENT_DESCRIPTION

                ContextShouldStay
            }
        }
    }

}

private object OtherContextInitializer: GameContextInitializer {

    override fun supportsEvent(event: ContextChangeEvent): Boolean {
        return event == EventChangingContextToSubsequent
    }

    override fun initialize() = OtherContext()

}

private const val OTHER_CONTEXT_INITIAL_DESCRIPTION = "Other context in it's initial state"
private const val OTHER_CONTEXT_SUBSEQUENT_DESCRIPTION = "Other context in it's subsequent state"

private class OtherContext: BaseGameContext() {

    private var description = OTHER_CONTEXT_INITIAL_DESCRIPTION

    override fun description() = description

    override fun availableActions() = listOf(DoSomethingWithinCurrentContext, GoBackToInitialContext)

    override fun performSupportedAction(action: Action): ContextActionResult {
        return when (action) {
            GoBackToInitialContext -> ContextCanBeClosed
            else -> {
                description = OTHER_CONTEXT_SUBSEQUENT_DESCRIPTION

                ContextShouldStay
            }
        }
    }

}