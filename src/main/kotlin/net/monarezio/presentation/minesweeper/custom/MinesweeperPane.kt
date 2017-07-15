package net.monarezio.presentation.minesweeper.custom

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import net.monarezio.presentation.values.AppColor
import tornadofx.*
import net.monarezio.domain.minesweeper.models.Field as DomainField

/**
 * Created by monarezio on 08/07/2017.
 */
class MinesweeperPane (private val listener: ClickListener): GridPane() {
    fun render(fields: List<List<DomainField>>) {
        clear()
        for (i in 0..fields.size - 1) {
            row {
                for (j in 0..fields.size - 1) {
                    label {

                        val field = fields[i][j]

                        text = when(field) {
                            DomainField.HIDDEN -> Field.EMPTY
                            DomainField.FLAG -> Field.FLAG
                            DomainField.QUESTION -> Field.QUESTION
                            else -> Field.EMPTY
                        }


                        alignment = Pos.CENTER

                        style {
                            minWidth = 32.px
                            minHeight = 32.px
                            maxHeight = 32.px
                            maxWidth = 32.px

                            if(field == DomainField.VISIBLE) {
                                textFill = AppColor.VISIBLE_FIELD
                                println("Hi")
                            }
                            else
                                textFill = AppColor.HIDDEN_FIELD
                            fontSize = 34.px
                            fontFamily = "FontAwesome"
                        }

                        if(field == DomainField.HIDDEN)
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