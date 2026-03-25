package com.github.aakumykov.compose_playground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
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

    val context = LocalContext.current
    val resources = LocalResources.current

    DropDownMenu(
        label = stringResource(R.string.drop_down_menu_label),
        optionList = Option.entries.toList(),
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(top = 48.dp),
        onOptionSelected = {
            Toast.makeText(context, Option.option2string(it,resources),Toast.LENGTH_SHORT).show()
        },
        option2string = Option.option2string
    )
}