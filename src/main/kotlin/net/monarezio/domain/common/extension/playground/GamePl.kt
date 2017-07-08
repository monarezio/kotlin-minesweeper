package net.monarezio.domain.common.extension.playground

import net.monarezio.domain.minesweeper.Game

/**
 * Created by monarezio on 08/07/2017.
 */

fun main(args: Array<String>) {
    val game = Game.createNewGame(10, 4)

    println(game.getBombPositions())
}