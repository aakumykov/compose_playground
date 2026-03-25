package com.github.aakumykov.compose_playground.utils

import kotlin.random.Random

val random: Random get() = Random

val randomBool: Boolean get() = random.nextBoolean()