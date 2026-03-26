package com.github.aakumykov.compose_playground.utils

import kotlin.random.Random

fun time2invoke(chancePercent: Int, block: (() -> Unit)? = null): Boolean {
    return if (Random.nextInt(1,101) <= chancePercent) {
        block?.invoke()
        true
    } else false
}