package net.monarezio.presentation.minesweeper

import javafx.stage.StageStyle
import net.monarezio.presentation.minesweeper.custom.ClickListener
import net.monarezio.presentation.minesweeper.custom.MinesweeperPane
import net.monarezio.presentation.minesweeper.models.Size
import tornadofx.*

/**
 * Created by monarezio on 08/07/2017.
 */
class Minesweeper : View("Minesweeper"), ClickListener {
    val controller: MinesweeperController by inject()

    private val grid: MinesweeperPane = MinesweeperPane(this)

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
                    item("5x5").action {
                        val scope = Scope()
                        setInScope(Size(5), scope)
                        find<Minesweeper>(scope).openModal()
                        close()
                    }
                    item("10x10")
                    item("25x25")
                }
            }
            menu("Help") {
                item("About")
            }
        }

        this += grid

        pane {
            style { minHeight = 4.px } //I have no idea how to make a margin on this thing ffs TODO
        }

        grid.render(controller.getDomain())
    }

    override fun onClick(x: Int, y: Int) {
        controller.onMove(x, y)
        grid.render(controller.getDomain())
    }
}
