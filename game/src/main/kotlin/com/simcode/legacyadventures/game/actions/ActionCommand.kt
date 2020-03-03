package com.simcode.legacyadventures.game.actions

data class ActionCommand(val commandBody: String, val commandParams: List<String> = emptyList())