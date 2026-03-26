package com.github.aakumykov.compose_playground.utils

import java.util.UUID

val newRandomId: String get() = UUID.randomUUID().toString()