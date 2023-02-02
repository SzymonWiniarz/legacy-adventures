package com.simcode.legacyadventures.ui.cli

import com.simcode.legacyadventures.game.Game
import com.simcode.legacyadventures.game.actions.Action
import com.simcode.legacyadventures.game.actions.Failure
import com.simcode.legacyadventures.game.actions.GameOver

class UI(private val game: Game) {

    fun startListeningForCommands() {
        var exitGame = false

        while (!exitGame) {
            println(game.description())
            val availableActions = game.availableActions()

            print("> ")
            val userCommand = readLine()
            println()

            val commandMatcher = CommandMatcher(availableActions)

            when {
                userCommand == null -> println("Type some command")
                userCommand == "exit" -> exitGame = true
                userCommand == "help" -> printAvailableActions(availableActions)
                commandMatcher.matchesAny(userCommand) -> exitGame = performAction(commandMatcher)
                else -> println("Unknown command")
            }
        }
    }

    private fun printAvailableActions(availableActions: List<Action>) {
        println("Available actions in current state:")
        availableActions
            .flatMap { it.triggeringCommands() }
            .map { it.commandBody }
            .plus("help")
            .plus("exit")
            .forEach { println(" - $it") }
        println()
    }

    private fun performAction(
        commandMatcher: CommandMatcher
    ): Boolean {
        val actionResult = game.performAction(commandMatcher.lastMatchedAction!!)

        if (actionResult is Failure) {
            println(actionResult.description)
        }

        return actionResult is GameOver
    }

    private class CommandMatcher(availableActions: List<Action>) {

        private val regexForActions: List<Pair<List<Regex>, Action>> = availableActions.map { action ->
            val regexForCurrentAction = action.triggeringCommands().map { Regex(it.commandBody) }
            Pair(regexForCurrentAction, action)
        }

        var lastMatchedAction: Action? = null
            private set

        fun matchesAny(userCommand: String): Boolean {
            val matchedAction = regexForActions.find {
                it.first.find { regex -> regex.matchEntire(userCommand) != null } != null
            }?.second

            lastMatchedAction = matchedAction

            return matchedAction != null
        }

    }
}