package net.monarezio.presentation.minesweeper.custom

import javafx.geometry.Pos
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import net.monarezio.domain.minesweeper.Minesweeper
import net.monarezio.domain.minesweeper.models.Coordinate
import net.monarezio.presentation.values.AppColor
import tornadofx.*
import net.monarezio.domain.minesweeper.models.Field as DomainField

/**
 * Created by monarezio on 08/07/2017.
 */
class MinesweeperPane (private val listener: ClickListener): GridPane() {
    fun render(domain: Minesweeper) {

        clear()
        val fields = domain.getFields()
        val isGameOver = domain.isGameOver()
        for (i in 0..fields.size - 1) {
            row {
                for (j in 0..fields.size - 1) {
                    label {

                        val field = fields[i][j]

                        alignment = Pos.CENTER

                        style {
                            minWidth = 32.px
                            minHeight = 32.px
                            maxHeight = 32.px
                            maxWidth = 32.px
                            textFill = AppColor.HIDDEN_FIELD
                            fontSize = 34.px
                            fontFamily = "FontAwesome"
                        }

                        when(field) {
                            DomainField.HIDDEN -> text = Field.EMPTY
                            DomainField.FLAG -> text = Field.FLAG
                            DomainField.QUESTION -> text = Field.QUESTION
                            else -> {
                                val value = domain.getValue(i, j)
                                text = value.toString()
                                style(true) {
                                    fontSize = 24.px
                                    textFill = Color((255.0 / 9 * (value + 1)) / 255, 0.0, 0.0, 1.0)
                                }
                            }
                        }

                        if(isGameOver && domain.getBombPositions().contains(Coordinate(i, j))) {
                            text = Field.BOMB
                            style(true) {
                                textFill = AppColor.BOMB
                                fontSize = 30.px
                            }
                        }

                        if(!isGameOver && field == DomainField.HIDDEN)
                            onHover { isHover ->
                                if(isHover)
                                    textFill = AppColor.HIDDEN_FIELD_HOVER
                                else
                                    textFill = AppColor.HIDDEN_FIELD
                            }

                        setOnMouseClicked {
                            listener.onClick(i, j)
                        }
                    }
                }
            }
        }
    }
}