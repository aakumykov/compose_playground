package com.github.aakumykov.compose_playground

import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _clickCounter = mutableIntStateOf(0)
    val clickCounter: IntState = _clickCounter

    fun onCounterClicked() {
        _clickCounter.intValue++
    }

    // TODO: Flow, LiveData
}