package com.github.aakumykov.compose_playground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                            .background(Color.Green)
                            .padding(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
//    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxSize()
    ) {

        DropDownMenu3(
            menuLabel = stringResource(R.string.drop_down_menu_label)
            , options = fakeOptionList
            , modifier = Modifier
            , onOptionSelected = { value: String ->
                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
            }
        )

        val text = "Простой текст"
        val textyState = rememberTextFieldState(text)
        val textFieldModifier = Modifier.fillMaxWidth()
        var simpleText by remember { mutableStateOf(text) }

        TextField(
            value = simpleText,
            onValueChange = { simpleText = it },
            modifier = textFieldModifier,
            label = { Text("Простое поле", color = MaterialTheme.colorScheme.primary) }
        )

        OutlinedTextField(
            state =textyState,
            modifier = textFieldModifier,
            label = { Text("Поле с каёмочкой", color = MaterialTheme.colorScheme.primary) }
        )
    }
}