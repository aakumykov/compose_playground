package com.github.aakumykov.compose_playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CounterWithButton(
    text: @Composable () -> Unit,
    button: @Composable (onClick: () -> Unit) -> Unit,
    modifier: Modifier = Modifier.Companion,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.background(Color.Companion.Yellow)
    ) {
        text()
        button(onButtonClick)
    }
}