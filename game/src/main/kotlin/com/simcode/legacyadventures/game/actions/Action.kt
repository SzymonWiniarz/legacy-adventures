package com.simcode.legacyadventures.game.actions

interface Action {

    fun triggeringCommands(): Set<ActionCommand>

}