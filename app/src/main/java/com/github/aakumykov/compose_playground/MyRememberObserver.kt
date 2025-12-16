package com.github.aakumykov.compose_playground

import android.util.Log
import androidx.compose.runtime.RememberObserver

class MyRememberObserver : RememberObserver {

    init {
        Log.d(TAG, "[${hashCode()}] init{}")
    }

    override fun onRemembered() {
        Log.d(TAG, "[${hashCode()}] onRemembered")
    }

    override fun onForgotten() {
        Log.d(TAG, "[${hashCode()}] onForgotten")
    }

    override fun onAbandoned() {
        Log.d(TAG, "[${hashCode()}] onAbandoned")
    }

    companion object {
        val TAG: String = MyRememberObserver::class.java.simpleName
    }
}