package com.simcode.legacyadventures.ui.cli

import com.simcode.legacyadventures.game.Game

class UI(private val game: Game) {

    private val goToRegex = Regex("go( to)? (?<passage>.+?)")

    fun startListeningForCommands() {
        var exitGame = false

        while (!exitGame) {
            println(game.description())
            val availableActions = game.availableActions()

            val userCommand = readLine()

            when {
                userCommand == null -> println("Type some command")
                userCommand == "exit" -> exitGame = true
//                goToRegex.matches(userCommand) -> {
//                    val changeLocationAction = findChangeLocationAction(userCommand, availableActions)
//                    if (changeLocationAction != null) {
//                        performAction(changeLocationAction)
//                    } else {
//                        println("Passage you specified doesn't exist")
//                    }
//                }
//                else -> println("Sorry, but this action is not available at the moment")
            }
        }
    }

//    private fun findChangeLocationAction(command: String, availableActions: List<Action>): ChangeLocation? {
//        val passageLabel = goToRegex.matchEntire(command)!!.groups.get("passage")?.value
//
//        return availableActions
//            .filterIsInstance<ChangeLocation>()
//            .find { it.passageLabel == passageLabel }
//    }
//
//    private fun performAction(action: Action) {
//        if (action is ChangeLocation) {
//            com.simcode.legacyadventures.game.switchLocation(action.targetLocationId)
//        } else {
//            throw IllegalArgumentException("Currently only ChangeLocation action is supported")
//        }
//    }

}