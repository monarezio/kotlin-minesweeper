package net.monarezio.presentation.minesweeper

import com.google.gson.GsonBuilder
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import net.monarezio.domain.minesweeper.Game
import net.monarezio.domain.minesweeper.Minesweeper
import net.monarezio.presentation.minesweeper.models.MinesweeperModel
import tornadofx.*
import java.io.File

/**
 * Created by monarezio on 10/07/2017.
 */
open class MinesweeperController: Controller() {

    private val model: MinesweeperModel by inject()

    var filePath = model.filePath
    private var domain = model.minesweeper

    private var areChanges = false

    fun onMove(event: MouseEvent, x: Int, y: Int) {
        if(!domain.isGameOver()) {
            if(event.button == MouseButton.PRIMARY)
                domain = domain.move(x, y)
            else
                domain = domain.secondaryMove(x, y)
            areChanges = false
        }
    }

    fun getDomain(): Minesweeper = domain

    fun areChanges() = areChanges

    fun saveState(file: File?): Boolean {
        var file = file
        if(file is File) {
            val gson = GsonBuilder().create()
            if(!file.getName().contains("."))
                file = File(file.getAbsolutePath() + ".mnp");

            file.bufferedWriter().use { i ->
                i.write(gson.toJson(domain))
            }
            return true
        }
        return false
    }

    fun loadState(file: File?): Minesweeper {
        if(file is File) {
            val gson = GsonBuilder().create()
            return gson.fromJson(file.bufferedReader().readText(), Game::class.java)
        }
        return Game.createEmpty()
    }
}