package net.monarezio.domain.minesweeper

import net.monarezio.domain.minesweeper.models.Coordinate
import net.monarezio.domain.minesweeper.models.Field

/**
 * Created by monarezio on 08/07/2017.
 */
interface Minesweeper: TimeListener {

    /**
     * gets the amount of one row/column
     */
    fun getSize(): Int

    /**
     * returns the number of bombs around the position
     */
    fun getValue(x: Int, y: Int): Int

    /**
     * returns amount of bombs on the field
     */
    fun getAmountOfBombs(): Int

    /**
     * returns the positions of all bombs
     */
    fun getBombPositions(): Set<Coordinate>

    /**
     * returns the position of every tagged/visible coordinate
     */
    fun getFields(): List<List<Field>>

    /**
     * makes the move
     */
    fun move(x: Int, y: Int): Minesweeper

    /**
     * makes the secondary move
     */
    fun secondaryMove(x: Int, y: Int): Minesweeper

    /**
     * returns true if game is over
     */
    fun isGameOver(): Boolean

    /**
     * returns if the player has won
     */
    fun hasWon(): Boolean

    /**
     * returns true if there are no bombs
     */
    fun isEmpty(): Boolean

    /**
     * get the current time in seconds
     */
    fun getCurrentTime(): Int

}