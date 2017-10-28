package net.monarezio.presentation.minesweeper.models

import net.monarezio.domain.minesweeper.Minesweeper
import tornadofx.*

data class MinesweeperModel(val minesweeper: Minesweeper, val filePath: String = ""): ViewModel()