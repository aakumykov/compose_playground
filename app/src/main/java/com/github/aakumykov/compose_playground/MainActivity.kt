package com.github.aakumykov.compose_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    viewModel: MainViewModel = viewModel(),
) {
    val constantState: IntState = remember { mutableIntStateOf(0) }
    val mutableIntState: MutableIntState = remember { mutableIntStateOf(1) }
    var numStateBY: Int by remember { mutableStateOf(0) }

    Column (modifier = modifier.fillMaxWidth()) {
        ClickCounter(
            label = "intState0 (неизменяемый)",
            counterValue = constantState.intValue,
            onCounterClicked = {},
        )

        ClickCounter(
            label = "mutableIntState1",
            counterValue = mutableIntState.intValue,
            onCounterClicked = { mutableIntState.intValue += 1 },
        )

        ClickCounter(
            label = "numStateBY",
            counterValue = numStateBY,
            onCounterClicked = { numStateBY += 1 },
        )

        ClickCounter(
            label = "viewModel.intState",
            counterValue = viewModel.clickCounterState.intValue,
            onCounterClicked = { viewModel.clickCounterState.intValue += 1 },
        )

        val constantIntState = viewModel.constantIntStateFlow.collectAsState()
        ClickCounter(
            label = "viewModel.constantIntStateFlow (неизменяемый)",
            counterValue = constantIntState.value,
            onCounterClicked = {  },
        )
    }
}

@Composable
fun ClickCounter(
    label: String,
    counterValue: Int,
    onCounterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(6.dp))
        .padding(vertical = 3.dp, horizontal = 6.dp)
        .clickable { onCounterClicked() }
    ) {
        Text(label, fontStyle = FontStyle.Italic, fontSize = 16.sp)
        Text(
            text = "Нажатий: $counterValue",
            fontSize = 30.sp,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_playgroundTheme {
        HomeScreen(modifier = Modifier.background(Color.Yellow).padding(12.dp))
    }
}