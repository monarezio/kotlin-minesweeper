package net.monarezio.presentation.minesweeper

import net.monarezio.domain.minesweeper.Game
import net.monarezio.domain.minesweeper.Minesweeper
import net.monarezio.presentation.minesweeper.custom.ClickListener
import net.monarezio.presentation.minesweeper.models.Size
import tornadofx.*

/**
 * Created by monarezio on 10/07/2017.
 */
open class MinesweeperController: Controller() {

    private val size: Size by inject(DefaultScope)

    private var domain = Game.createNewGame(size.size, size.size / 2)

    fun onMove(x: Int, y: Int) {
        if(!domain.isGameOver())
            domain = domain.move(x, y)
    }

    fun getDomain(): Minesweeper = domain

    fun newGame(size: Int) {
        domain = Game.createNewGame(size, size / 2)
    }
}