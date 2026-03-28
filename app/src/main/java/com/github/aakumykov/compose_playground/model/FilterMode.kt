package com.github.aakumykov.compose_playground.model

import android.content.res.Resources

enum class FilterMode {
    WHITE,
    BLACK;

    companion object {
        val random: FilterMode get() = entries.random()

        val enum2string = fun(value: FilterMode, resources: Resources): String = when(value){
            BLACK -> resources.getString(com.github.aakumykov.compose_playground.R.string.filter_mode_black)
            WHITE -> resources.getString(com.github.aakumykov.compose_playground.R.string.filter_mode_white)
        }
    }
}