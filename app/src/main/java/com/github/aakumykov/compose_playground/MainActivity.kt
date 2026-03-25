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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.aakumykov.compose_playground.ui.theme.Compose_playgroundTheme
import kotlin.random.Random

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

class Abc(val num: Int) {
    override fun toString(): String = Abc::class.java.simpleName + "-" + num
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    val counterState: MutableIntState = rememberSaveable { mutableIntStateOf(viewModel.counterState.intValue) }

    val qwertyState: MutableState<Qwerty> = rememberSaveable(stateSaver = QwertySaver()) {
        mutableStateOf(viewModel.qwertyState.value)
    }

    val abcState = remember { mutableStateOf(Abc(0)) }


    Column (modifier = modifier.fillMaxWidth()) {
        Text(
            text = counterState.intValue.toString(),
            modifier = Modifier
                .clickable {
                    viewModel.updateCounter(Random.nextInt(1,101))
                }
                .background(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
        Text(
            text = qwertyState.value.toString(),
            modifier = Modifier
                .clickable {
                    qwertyState.value = Qwerty(qwertyState.value.num+1)
                }
                .background(color = Color(0xFFE1F5FE), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
                .align(Alignment.End)
        )
        Text(
            text = abcState.value.toString(),
            modifier = Modifier
                .clickable {
                    abcState.value = Abc(abcState.value.num+1)
                }
                .padding(end = 40.dp)
                .background(color = Color(0xFFFFFDE7), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
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