package com.simcode.legacyadventures.mainmenu.context

import com.simcode.legacyadventures.events.ContextChangeEvent
import com.simcode.legacyadventures.events.GameStarted
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainMenuContextInitializerTest {

    private lateinit var mainMenuContextInitializer: MainMenuContextInitializer

    @BeforeEach
    fun setUp() {
        mainMenuContextInitializer = MainMenuContextInitializer
            .configure()
            .withAvailableAdventures(listOf("available adventure"))
            .withPendingAdventures(listOf("pending adventure"))
            .build()
    }

    @Test
    fun `should support GameStarted event`() {
        //when
        val eventSupported = mainMenuContextInitializer.supportsEvent(GameStarted)

        //then
        assertThat(eventSupported).isTrue()
    }

    @Test
    fun `should not support event different than GameStarted`() {
        //when
        val eventSupported = mainMenuContextInitializer.supportsEvent(OtherEvent)

        //then
        assertThat(eventSupported).isFalse()
    }

    @Test
    fun `should properly initialize MainMenuContext`() {
        //when
        val gameContext = mainMenuContextInitializer.initialize(GameStarted)

        //then
        assertThat(gameContext is MainMenuContext).isTrue()
    }

    // -------- events ---------- //

    private object OtherEvent: ContextChangeEvent

}