package net.monarezio

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.text.Font
import javafx.stage.Stage
import net.monarezio.domain.minesweeper.Game
import net.monarezio.presentation.minesweeper.Minesweeper
import net.monarezio.presentation.minesweeper.models.MinesweeperModel
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
        setInScope(MinesweeperModel(Game.createNewGame(10, 5)), DefaultScope)
    }

    companion object {
        fun main(args: Array<String>) {
            Application.launch(Run::class.java, *args)
        }
    }
}