package com.github.aakumykov.compose_playground

import android.util.Log
import androidx.compose.runtime.RememberObserver

class MyRememberObserver(private val label: String) : RememberObserver {

    init {
        Log.d(TAG, "($label) [${hashCode()}] init{}")
    }

    override fun onRemembered() {
        Log.d(TAG, "($label) [${hashCode()}] onRemembered")
    }

    override fun onForgotten() {
        Log.d(TAG, "($label) [${hashCode()}] onForgotten")
    }

    override fun onAbandoned() {
        Log.d(TAG, "($label) [${hashCode()}] onAbandoned")
    }

    companion object {
        val TAG: String = MyRememberObserver::class.java.simpleName
    }
}