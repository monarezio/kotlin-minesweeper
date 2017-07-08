package net.monarezio.presentation

import javafx.scene.control.Menu
import javafx.scene.layout.*
import javafx.scene.paint.Color
import net.monarezio.domain.minesweeper.Game
import tornadofx.*

/**
 * Created by monarezio on 08/07/2017.
 */
class Minesweeper : View("Minesweeper") {

    val domain = Game.createNewGame(10, 2)

    override val root = vbox {
        menubar {
            menu("File") {
                item("Save", "Ctrl + S")
                item("Load")
                item("Exit", "Alt + F4").action {
                    System.exit(0)
                }
            }
            menu("Game") {
                item("New", "Ctrl + N")
                menu("Presets") {
                    item("5x5")
                    item("10x10")
                    item("25x25")
                }
            }
            menu("Help") {
                item("About")
            }
        }

        gridpane {
            for (i in 0..domain.getSize()) {
                row {
                    for (j in 0..domain.getSize()) {
                        pane {
                            style {
                                minHeight = 32.px
                                minWidth = 32.px

                                borderWidth += box(0.5.px)
                                borderStyle += BorderStrokeStyle.SOLID
                                borderColor += box(Color.GRAY)
                            }
                        }
                    }
                }
            }
        }
    }
}
