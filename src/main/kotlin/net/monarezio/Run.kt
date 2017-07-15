package net.monarezio

import javafx.application.Application
import javafx.scene.text.Font
import javafx.stage.Stage
import net.monarezio.presentation.minesweeper.Minesweeper
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
        Font.loadFont(Run::class.java.getResource("/fonts/FontAwesome.otf").toExternalForm(), 10.0)
    }

    companion object {
        fun main(args: Array<String>) {
            Application.launch(Run::class.java, *args)
        }
    }
}