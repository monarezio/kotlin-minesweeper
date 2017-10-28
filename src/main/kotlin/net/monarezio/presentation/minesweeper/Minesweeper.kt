package net.monarezio.presentation.minesweeper

import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.MouseEvent
import javafx.stage.FileChooser
import javafx.util.Duration
import net.monarezio.domain.minesweeper.Game
import net.monarezio.presentation.minesweeper.custom.ClickListener
import net.monarezio.presentation.minesweeper.custom.MinesweeperPane
import net.monarezio.presentation.minesweeper.models.MinesweeperModel
import tornadofx.*
import java.io.File
import java.time.Duration.ofMillis


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
                item("New", "Ctrl + N").action {
                    dialog("New Game") {
                        val textField = textfield(controller.getDomain().getSize().toString())
                        fieldset {
                            field("Size") {
                                this += textField
                            }
                        }
                        button("Create").action {
                            val size = textField.text.toIntOrNull()
                            if(size is Int)
                                newGame(size)
                            else
                                alert(Alert.AlertType.ERROR, "Size can only be a number")
                        }
                    }
                }
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

        label {
            text = controller.getDomain().getCurrentTime().toString()
            val timeline = Timeline(KeyFrame(1.seconds, EventHandler {
                controller.getDomain().onNewInterval()
                text = controller.getDomain().getCurrentTime().toString()
            }))
            timeline.cycleCount = Animation.INDEFINITE
            timeline.play()
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

    override fun onClick(event: MouseEvent, x: Int, y: Int) {
        controller.onMove(event, x, y)
        grid.render(controller.getDomain())

        if(controller.getDomain().isGameOver()) {
            if(controller.getDomain().hasWon())
                alert(Alert.AlertType.CONFIRMATION, "You won!")
            else
                alert(Alert.AlertType.ERROR, "You loose!")
        }
    }

    fun onCloseHandle(callback: () -> Unit = {}) { //TODO: figure out weird javaFX event handlers
        if(!controller.getDomain().isGameOver()) { //TODO: messy code!!!
            alert(Alert.AlertType.WARNING, "Save progress", "Do you want to save your progress?", ButtonType.CANCEL, ButtonType.NO, ButtonType.YES) { type ->
                var success = true
                if (type == ButtonType.NO)
                    currentStage!!.close()
                else if (type == ButtonType.YES) {
                    success = if (!controller.filePath.isBlank()) {
                        controller.saveState(chooseFile("Save", arrayOf(FileChooser.ExtensionFilter("Minesweeper save file (*.mnp)", "*.mnp")), FileChooserMode.Save) {
                            initialDirectory = File(System.getProperty("user.home"))
                        }.firstOrNull())
                    } else
                        controller.saveState(File(controller.filePath))

                    if (success)
                        currentStage!!.close()
                }

                if (success && type != ButtonType.CANCEL)
                    callback.invoke()
            }
        } else {
            currentStage!!.close()
            callback.invoke()
        }
    }
}
