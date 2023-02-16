package com.simcode.legacyadventures.exploration.context

import com.simcode.legacyadventures.events.ContextChangeEvent
import com.simcode.legacyadventures.events.NewAdventureStarted
import com.simcode.legacyadventures.events.PendingAdventureStarted
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ExplorationContextInitializerTest {

    private lateinit var explorationContextInitializer: ExplorationContextInitializer

    @BeforeEach
    fun setUp() {
        explorationContextInitializer = ExplorationContextInitializer.build()
    }

    @Test
    fun `should support NewAdventureStarted event`() {
        //when
        val eventSupported = explorationContextInitializer.supportsEvent(NewAdventureStarted("Epic Adventure"))

        //then
        assertThat(eventSupported).isTrue()
    }

    @Test
    fun `should support PendingAdventureStarted event`() {
        //when
        val eventSupported = explorationContextInitializer.supportsEvent(PendingAdventureStarted("Pending Epic Adventure"))

        //then
        assertThat(eventSupported).isTrue()
    }

    @Test
    fun `should not support other events`() {
        //when
        val eventSupported = explorationContextInitializer.supportsEvent(OtherEvent)

        //then
        assertThat(eventSupported).isFalse()
    }

    @Test
    fun `should properly initialize ExplorationContext`() {
        //when
        val gameContext = explorationContextInitializer.initialize(NewAdventureStarted("sample_adventure"))

        //then
        assertThat(gameContext is ExplorationContext).isTrue()
    }

    // -------- events ---------- //

    private object OtherEvent: ContextChangeEvent

}