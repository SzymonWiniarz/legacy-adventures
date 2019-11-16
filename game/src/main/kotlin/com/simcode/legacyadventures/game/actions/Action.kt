package com.simcode.legacyadventures.game.actions

interface Action {

    fun type(): String

    fun targetLabels(): List<String>

}