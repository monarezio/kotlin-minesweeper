package net.monarezio.presentation.minesweeper

import com.google.gson.GsonBuilder
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.FileChooser
import net.monarezio.domain.minesweeper.Game
import net.monarezio.presentation.minesweeper.custom.ClickListener
import net.monarezio.presentation.minesweeper.custom.MinesweeperPane
import net.monarezio.presentation.minesweeper.models.MinesweeperModel
import tornadofx.*
import java.io.File

/**
 * Created by monarezio on 08/07/2017.
 */
class Minesweeper : View("Minesweeper"), ClickListener {

    val controller: MinesweeperController by inject()

    private val grid: MinesweeperPane = MinesweeperPane(this)

    override val root = vbox {
        menubar {
            menu("File") {
                item("Save", "Ctrl + S").action {
                    if(controller.filePath.isBlank())
                        controller.saveState(chooseFile("Save", arrayOf(FileChooser.ExtensionFilter("Minesweeper save file (*.mnp)", "*.mnp")), FileChooserMode.Save) {
                            initialDirectory = File(System.getProperty("user.home"))
                        }.firstOrNull())
                    else
                        controller.saveState(File(controller.filePath))
                }
                item("Load").action {
                    val newGame = controller.loadState(chooseFile("Load", arrayOf(FileChooser.ExtensionFilter("Minesweeper save file (*.mnp)", "*.mnp")), FileChooserMode.Single) {
                        initialDirectory = File(System.getProperty("user.home"))
                    }.firstOrNull())

                    if(!newGame.isEmpty()) {
                        val scope = Scope()
                        setInScope(MinesweeperModel(newGame), scope)
                        onCloseHandle {
                            find<Minesweeper>(scope).openWindow(resizable = false, owner = null)
                        }
                    }
                }
                item("Exit", "Alt + F4").action {
                    onCloseHandle()
                }
            }
            menu("Game") {
                item("New", "Ctrl + N")
                menu("Presets") {
                    item("5x5").action {
                        newGame(5)
                    }
                    item("10x10").action {
                        newGame(10)
                    }
                    item("25x25").action {
                        newGame(25)
                    }
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

    private fun newGame(size: Int) {
        val scope = Scope()
        setInScope(MinesweeperModel(Game.createNewGame(size, size / 2)), scope)
        onCloseHandle {
            find<Minesweeper>(scope).openWindow(resizable = false, owner = null)
        }
    }

    override fun onClick(x: Int, y: Int) {
        controller.onMove(x, y)
        grid.render(controller.getDomain())
    }

    fun onCloseHandle(callback: () -> Unit = {}) { //TODO: figure out weird javaFX event handlers
        alert(Alert.AlertType.WARNING, "Save progress", "Do you want to save your progress?", ButtonType.CANCEL, ButtonType.NO, ButtonType.YES) { type ->
            var success = true
            if(type == ButtonType.NO)
                currentStage!!.close()
            else if(type == ButtonType.YES) {
                success = if(!controller.filePath.isBlank()) {
                        controller.saveState(chooseFile("Save", arrayOf(FileChooser.ExtensionFilter("Minesweeper save file (*.mnp)", "*.mnp")), FileChooserMode.Save) {
                                initialDirectory = File(System.getProperty("user.home"))
                            }.firstOrNull())
                    } else
                        controller.saveState(File(controller.filePath))

                if(success)
                    currentStage!!.close()
            }

            if(success && type != ButtonType.CANCEL)
                callback.invoke()
        }
    }
}
