package net.monarezio

import javafx.application.Application
import javafx.stage.Stage
import net.monarezio.presentation.Minesweeper
import tornadofx.*

/**
 * Created by monarezio on 08/07/2017.
 */

class Run: App(Minesweeper::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.isResizable = false
    }

    init {

    }

    companion object {
        fun main(args: Array<String>) {
            Application.launch(Run::class.java, *args)
        }
    }
}