package net.monarezio.domain.common.extension

/**
 * Created by monarezio on 15/07/2017.
 */

fun<E> List<E>.set(index: Int, value: E): List<E> {
    return mapIndexed { i, e ->
        if(i == index) value
        else e
    }
}

fun<E> List<List<E>>.set(x: Int, y: Int, value: E): List<List<E>> {
    return set(x, get(x).set(y, value))
}