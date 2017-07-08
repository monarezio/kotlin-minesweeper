package net.monarezio.domain.minesweeper

import net.monarezio.domain.common.utils.SetUtil
import net.monarezio.domain.minesweeper.models.Coordinate

/**
 * Created by monarezio on 08/07/2017.
 */
class Game private constructor(private val bombs: Set<Coordinate>, private val size: Int) : Minesweeper {

    override fun getSize(): Int = size

    override fun getAmountOfBombs(): Int = bombs.size

    override fun getAmountOfBombsAround(x: Int, y: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBombPositions(): Set<Coordinate> = bombs

    companion object {
        fun createNewGame(size: Int, bombCount: Int): Minesweeper = Game(SetUtil.instance.getRandomCoordinates(bombCount - 1, 0, size - 1), size)
    }
}