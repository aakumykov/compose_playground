package com.github.aakumykov.compose_playground

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val clickCounterState = mutableIntStateOf(0)

    val constantIntStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    // TODO: Flow, LiveData
}