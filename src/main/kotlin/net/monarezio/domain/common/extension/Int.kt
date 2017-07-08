package net.monarezio.domain.common.extension

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by monarezio on 08/07/2017.
 */

/**
 * Returns a random int between var lower and upper (both including)
 */
fun Int.Companion.random (lower: Int , upper: Int) = ThreadLocalRandom.current().nextInt(lower, upper + 2);