package com.github.aakumykov.compose_playground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.aakumykov.compose_playground.ui.theme.Compose_playgroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_playgroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    val counter = rememberSaveable { mutableIntStateOf(0) }

    CounterWithButton(
        modifier = modifier,
        text = {
            Text(
                text = "Счётчик=${counter.intValue}",
                modifier = Modifier.background(Color.Magenta)
            )
        },
        button = { onClick ->
            Button(
                onClick = onClick,
                modifier = Modifier.background(Color.Cyan)
            ) {
                Text("Увеличить щёччик", modifier = Modifier.background(Color.Red))
            }
        },
        onButtonClick = {
            counter.intValue++
            Log.d("щёччик", "counter=${counter.intValue}")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_playgroundTheme {
        Greeting()
    }
}