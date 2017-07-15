package net.monarezio.domain.minesweeper

import net.monarezio.domain.common.extension.set
import net.monarezio.domain.common.utils.SetUtil
import net.monarezio.domain.minesweeper.models.Coordinate
import net.monarezio.domain.minesweeper.models.Field
import tornadofx.*

/**
 * Created by monarezio on 08/07/2017.
 */
class Game private constructor(
        private val bombs: Set<Coordinate>,
       private val size: Int,
       private val fields: List<List<Field>> = 0.rangeTo(size).map { 0.rangeTo(size).map { Field.HIDDEN } }
) : ViewModel(), Minesweeper {

    override fun getSize(): Int = size

    override fun getAmountOfBombs(): Int = bombs.size

    override fun getAmountOfBombsAround(x: Int, y: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBombPositions(): Set<Coordinate> = bombs

    override fun getFields(): List<List<Field>> = fields

    override fun move(x: Int, y: Int): Minesweeper {
        return Game(bombs, size, fields.set(x, y, Field.VISIBLE))
    }

    companion object {
        fun createNewGame(size: Int, bombCount: Int): Minesweeper = Game(SetUtil.instance.getRandomCoordinates(bombCount - 1, 0, size - 1), size)
    }
}