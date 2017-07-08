package net.monarezio.domain.common.utils

import net.monarezio.domain.common.extension.random
import net.monarezio.domain.minesweeper.models.Coordinate

/**
 * Created by monarezio on 08/07/2017.
 */
class SetUtil private constructor() {

    fun getRandomCoordinates(amount: Int, lower: Int, upper: Int): Set<Coordinate> {
        fun helper(amount: Int, coordinates: Set<Coordinate> = setOf()): Set<Coordinate> {
            val coordinate = Coordinate(Int.random(lower, upper - 1), Int.random(lower, upper - 1))

            if(coordinates.contains(coordinate))
                return helper(amount, coordinates) //If on the current position the bomb exists
            else if(amount == 0)
                return (coordinates + coordinate) //If there is no more coordinates to add
            else
                return helper(amount - 1, coordinates + coordinate) //Simple loop
        }

        return helper(amount)
    }

    companion object {
        val instance = SetUtil()
    }

}