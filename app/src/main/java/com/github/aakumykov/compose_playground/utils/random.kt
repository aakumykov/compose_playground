package com.github.aakumykov.compose_playground.utils

import com.github.javafaker.Faker
import java.util.Locale
import kotlin.random.Random

val random: Random get() = Random

val faker: Faker by lazy { Faker(Locale.getDefault()) }

val randomString: String get() = faker.address().cityName()

val randomBool: Boolean get() = random.nextBoolean()