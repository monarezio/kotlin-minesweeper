package net.monarezio.presentation.minesweeper.custom

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.layout.*
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import net.monarezio.domain.minesweeper.Minesweeper
import net.monarezio.domain.minesweeper.models.Coordinate
import net.monarezio.presentation.values.AppColor
import tornadofx.*
import java.io.BufferedReader
import java.io.File
import java.net.URI
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

                style {
                    backgroundColor += Color.BLACK
                }

                for (j in 0..fields.size - 1) {
                    label {
                        val field = fields[i][j]

                        alignment = Pos.CENTER

                        style {
                            minWidth = 34.px
                            minHeight = 34.px
                            maxHeight = 34.px
                            maxWidth = 34.px
                            fontSize = 30.px
                            fontFamily = "FontAwesome"

                            borderStyle += BorderStrokeStyle.SOLID
                            borderWidth += box(1.px)
                            borderColor += box(AppColor.BORDER_FIELD)
                            borderRadius += box(2.px)

                            backgroundColor += AppColor.HIDDEN_FIELD
                            backgroundRadius += box(2.px)
                        }

                        when(field) {
                            DomainField.HIDDEN -> {
                                style(true) {

                                }
                            }
                            DomainField.FLAG -> {
                                text = Field.FLAG

                                style(true) {
                                    textFill = AppColor.FLAG
                                }
                            }
                            DomainField.QUESTION -> {
                                text = Field.QUESTION

                                style(true) {
                                    textFill = AppColor.QUESTION
                                }
                            }
                            else -> {
                                val value = domain.getValue(i, j)
                                if(value != 0)
                                    text = value.toString()
                                style(true) {
                                    fontSize = 24.px
                                    textFill = Color((255.0 / 9 * (value + 1)) / 255, 0.0, 0.0, 1.0)
                                    backgroundColor += Color.WHITE
                                }
                            }
                        }

                        if(isGameOver && domain.getBombPositions().contains(Coordinate(i, j))) {
                            text = Field.BOMB

                            style(true) {
                                textFill = AppColor.BOMB
                                fontSize = 24.px
                                backgroundColor += Color.WHITE
                            }
                        }

                        if(!isGameOver && field == DomainField.HIDDEN)
                            onHover { isHover ->
                                if(isHover)
                                    style(true) {
                                        backgroundColor += AppColor.HIDDEN_FIELD_HOVER
                                    }
                                else
                                    style(true) {
                                        backgroundColor += AppColor.HIDDEN_FIELD
                                    }
                            }

                        setOnMouseClicked { event ->
                            listener.onClick(event, i, j)
                        }
                    }
                }
            }
        }
    }
}