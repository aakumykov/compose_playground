package com.github.aakumykov.compose_playground.data.model

enum class FilterMode {
    WHITE,
    BLACK;

    companion object {
        val random: FilterMode get() = FilterMode.entries.random()
    }
}