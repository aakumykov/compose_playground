package com.github.aakumykov.compose_playground.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.aakumykov.compose_playground.ui.theme.Error

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier.Companion
) {
    Text(
        text = text,
        color = Error,
        modifier = modifier
    )
}