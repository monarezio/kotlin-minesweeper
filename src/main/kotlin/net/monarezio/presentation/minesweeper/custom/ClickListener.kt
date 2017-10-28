package net.monarezio.presentation.minesweeper.custom

import javafx.scene.input.MouseEvent

/**
 * Created by monarezio on 10/07/2017.
 */
interface ClickListener {

    fun onClick(event: MouseEvent, x: Int, y: Int)

}