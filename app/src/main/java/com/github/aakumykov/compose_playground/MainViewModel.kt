package com.github.aakumykov.compose_playground

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {
    val counterState: MutableIntState = mutableIntStateOf(0)
    val counterFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    val qwertyState: MutableState<Qwerty> = mutableStateOf(Qwerty(0))
    val qwertyFlow: MutableStateFlow<Qwerty> = MutableStateFlow(Qwerty(0))

    fun updateNumInQwerty(newNum: Int) {
        val qwerty = qwertyState.value
        qwerty.num = newNum
        qwertyState.value = qwerty
    }

    fun updateCounter(newValue: Int) {
        counterState.intValue = newValue
    }
}

data class Qwerty(var num: Int) {
    //    override fun toString(): String = Qwerty::class.simpleName + "-" + num
    override fun hashCode(): Int = num

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Qwerty

        if (num != other.num) return false

        return true
    }
}

class QwertySaver : Saver<Qwerty,Int> {
    override fun restore(value: Int): Qwerty? = Qwerty(value)
    override fun SaverScope.save(value: Qwerty): Int? = value.num
}