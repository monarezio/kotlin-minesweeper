package net.monarezio.domain.minesweeper.models

/**
 * Created by monarezio on 10/07/2017.
 */
enum class Field {
    FLAG, QUESTION, VISIBLE, HIDDEN;

    fun toggle(): Field = when (this) {
            FLAG -> QUESTION
            QUESTION -> HIDDEN
            else -> FLAG
        }
}