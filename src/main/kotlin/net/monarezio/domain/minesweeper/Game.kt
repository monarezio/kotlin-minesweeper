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

    override fun getValue(x: Int, y: Int): Int {
        return (x - 1).rangeTo(x + 1).map { i ->
            (y - 1).rangeTo(y + 1).filter {
                j -> bombs.contains(Coordinate(i, j))
            }.count()
        }.sum()
    }

    override fun getBombPositions(): Set<Coordinate> = bombs

    override fun getFields(): List<List<Field>> = fields

    override fun move(x: Int, y: Int): Minesweeper {
        var tmpGame: Minesweeper = Game(bombs, size, fields.set(x, y, Field.VISIBLE))
        if(getValue(x, y) == 0) {
            if(isInFieldAndIsHidden(x + 1, y)) tmpGame = tmpGame.move(x + 1, y)
            if(isInFieldAndIsHidden(x - 1, y)) tmpGame = tmpGame.move(x - 1, y)
            if(isInFieldAndIsHidden(x, y + 1)) tmpGame = tmpGame.move(x, y + 1)
            if(isInFieldAndIsHidden(x, y - 1)) tmpGame = tmpGame.move(x, y - 1)
        }
        return tmpGame
    }

    override fun isGameOver(): Boolean = !bombs.all { i -> fields[i.x][i.y] != Field.VISIBLE }

    private fun isInFieldAndIsHidden(x: Int, y: Int) = (x >= 0 && x <= size && y >= 0 && y <= size) && fields[x][y] == Field.HIDDEN

    companion object {
        fun createNewGame(size: Int, bombCount: Int): Minesweeper = Game(SetUtil.instance.getRandomCoordinates(bombCount - 1, 0, size - 1), size)
    }
}