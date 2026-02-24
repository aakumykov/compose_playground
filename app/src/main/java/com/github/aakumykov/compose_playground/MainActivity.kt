package com.github.aakumykov.compose_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val list = remember { mutableStateOf<List<String>>(listOf(
        "Маша", "Даша", "Глаша"
    )) }

    Column (modifier = modifier.fillMaxSize().background(Color(0xFFFFFBE9))) {
        Text("--------------- начало ----------------", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = list.value, key = { it }) { listItem ->
                Text(
                    listItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                HorizontalDivider(thickness = 1.dp)
            }
        }

        Text("--------------- конец -----------------", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить список")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_playgroundTheme {
        Greeting()
    }
}