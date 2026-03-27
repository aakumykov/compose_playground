package com.github.aakumykov.compose_playground.model

enum class FilterMode {
    WHITE,
    BLACK;

    companion object {
        val random: FilterMode get() = entries.random()
    }
}