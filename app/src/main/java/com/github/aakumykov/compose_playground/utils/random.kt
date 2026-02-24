package com.github.aakumykov.compose_playground.utils

import java.util.UUID
import kotlin.random.Random

val random: Random by lazy { Random }

val newRandomId: String get() = UUID.randomUUID().toString()

val randomBool: Boolean get() = random.nextBoolean()