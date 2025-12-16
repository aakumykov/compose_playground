package com.github.aakumykov.compose_playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.aakumykov.compose_playground.repository.BooksRepository
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBook
import com.github.aakumykov.compose_playground.ui.theme.Compose_playgroundTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_playgroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        onButton1Clicked = {
                            lifecycleScope.launch (Dispatchers.IO) {
                                appDatabase
                                    .getBookDAO()
                                    .addBook(RoomBook.create())
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        /*lifecycleScope.launch {
            BooksRepository(appDatabase.getBookDAO())
                .addBook(RoomBook.create())
        }*/
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onButton1Clicked: () -> Unit,
    mainViewModel: MainViewModel = viewModel(),
) {
    Column {
        ClickCounter(
            counterValue = mainViewModel.clickCounter.intValue,
            onCounterClicked = mainViewModel::onCounterClicked,
            modifier = modifier
        )
        Button(onClick = onButton1Clicked, modifier = Modifier.fillMaxWidth()) {
            Text("Добавить книгу в БД (lifecycleScope)")
        }
        Button(onClick = {

        }, modifier = Modifier.fillMaxWidth()) {
            Text("Добавить книгу в БД (rememberCoroutineScope)", textAlign = TextAlign.Center)
        }
    }
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
        HomeScreen(onButton1Clicked = {})
    }
}