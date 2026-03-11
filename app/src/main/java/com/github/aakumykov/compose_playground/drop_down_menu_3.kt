package com.github.aakumykov.compose_playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.aakumykov.compose_playground.utils.randomString

val fakeOptionList by lazy { buildList { repeat(5) { add(randomString) } } }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DropDownMenu3(
    menuLabel: String,
    options: List<String>,
    onOptionSelected: (optionItem:String) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedOption: String? = null,
    isExpandedByDefault: Boolean = false,
) {
    var expanded: Boolean by remember { mutableStateOf(isExpandedByDefault) }
    val textFieldState = rememberTextFieldState(initialSelectedOption ?: "")
    var checkedIndex: Int? by remember { mutableStateOf(options.indexOf(initialSelectedOption)) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        },
        modifier = modifier
    ) {
        TextField(
            state = textFieldState,
            readOnly = true,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = { Text(menuLabel) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .background(Color.Cyan)
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.groupStandardContainerColor,
            shape = MenuDefaults.standaloneGroupShape,
        ) {
            options.forEachIndexed { index, optionText ->
                DropdownMenuItem(
                    text = { Text(optionText, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        checkedIndex = index
                        textFieldState.setTextAndPlaceCursorAtEnd(optionText)
                        expanded = false
                        onOptionSelected.invoke(textFieldState.text.toString())
                    },
                    selected = index == checkedIndex,
                    selectedLeadingIcon = {
                        Icon(
                            Icons.Default.Check,
                            modifier = Modifier.size(MenuDefaults.LeadingIconSize),
                            contentDescription = null,
                        )
                    },
                    shapes = MenuDefaults.itemShape(index, options.size),
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DropDownMenu3Preview() {
    DropDownMenu3(
        menuLabel = stringResource(R.string.drop_down_menu_label),
        options = fakeOptionList,
        modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(top = 48.dp)
        ,
        onOptionSelected = { value: String ->  }
    )
}