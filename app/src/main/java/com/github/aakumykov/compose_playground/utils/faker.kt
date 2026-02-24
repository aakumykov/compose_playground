package com.github.aakumykov.compose_playground.utils

import com.github.javafaker.Faker
import java.util.Locale

val faker: Faker by lazy { Faker(Locale("ru")) }