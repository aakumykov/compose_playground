package com.github.aakumykov.compose_playground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.aakumykov.compose_playground.ui.theme.Compose_playgroundTheme
import com.github.aakumykov.compose_playground.utils.fakeName
import com.github.aakumykov.compose_playground.utils.time2invoke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    val list by ListHolder.persons.collectAsState()
//    val items by viewModel.itemsFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Column (modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFFFFBE9))) {
        Text("--------------- начало ----------------", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = list, key = { it.hashCode() }) {
                DisplayedListItem(it)
                HorizontalDivider(thickness = 1.dp)
            }
        }

        Text("--------------- конец -----------------", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                scope.launch { ListHolder.updateList() }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить список")
        }
    }
}


@Composable
fun DisplayedListItem(person: Person, modifier: Modifier = Modifier) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            person.name,
            Modifier
//                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = person.age.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
//                .fillMaxSize()
        )
    }
}

@Preview
@Composable
fun DisplayedListItemPreview() {
    DisplayedListItem(Person.random())
}



object ListHolder {
    private val list: MutableList<Person> = mutableListOf<Person>().apply { addAll(Person.randomList()) }
    private val _persons = MutableStateFlow<List<Person>>(list)
    val persons: StateFlow<List<Person>> get() = _persons

    suspend fun updateList() {
        fun listString() = list.joinToString(",")

        if (time2invoke(33) && list.size <= 5) {
            list.add(Person.random())
            Log.d("updateList", "добавление в список: ${listString()}")
        } else if (time2invoke(33) && list.size >= 2) {
            list.remove(list.random())
            Log.d("updateList", "удаление из списка: ${listString()}")
        } else {
            val index = list.indexOf(list.random())
            list[index] = Person.random()
            Log.d("updateList", "обновление списка: ${listString()}")
        }

        _persons.emit(list)
    }
}