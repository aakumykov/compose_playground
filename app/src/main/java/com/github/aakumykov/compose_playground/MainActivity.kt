package com.github.aakumykov.compose_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.aakumykov.compose_playground.ui.theme.Compose_playgroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_playgroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
) {
    ClickCounter(
        counterValue = mainViewModel.clickCounter.intValue,
        onCounterClicked = mainViewModel::onCounterClicked,
        modifier = modifier
    )
}

@Composable
fun ClickCounter(
    counterValue: Int,
    onCounterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Нажатий: $counterValue",
        fontSize = 30.sp,
        modifier = modifier.clickable { onCounterClicked() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_playgroundTheme {
        HomeScreen()
    }
}