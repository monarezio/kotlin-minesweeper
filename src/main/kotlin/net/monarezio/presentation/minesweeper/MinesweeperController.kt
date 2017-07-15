package net.monarezio.presentation.minesweeper

import net.monarezio.domain.minesweeper.Game
import net.monarezio.presentation.minesweeper.custom.ClickListener
import tornadofx.*

/**
 * Created by monarezio on 10/07/2017.
 */
class MinesweeperController: Controller() {
    private var domain = Game.createNewGame(10, 4)

    fun onMove(x: Int, y: Int) {
        domain = domain.move(x, y)
    }

    fun getFields() = domain.getFields()

    fun newGame(size: Int) {
        domain = Game.createNewGame(size, size / 2)
    }
}