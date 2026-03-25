package com.github.aakumykov.compose_playground

import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> DropDownMenu(
    label: String,
    modifier: Modifier = Modifier,
    optionList: List<T>,
    preselectedOption: T? = null,
    onOptionSelected: (option: T) -> Unit,
    option2string: (option: T, resources: Resources) -> String,
    resources: Resources = LocalResources.current
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState(preselectedOption?.let { option2string(it, resources) } ?: "")
    var checkedIndex: Int? by remember { mutableStateOf(optionList.indexOf(preselectedOption)) }
    var selectedItem: T? by remember { mutableStateOf(null) }

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
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MenuDefaults.groupStandardContainerColor,
            shape = MenuDefaults.standaloneGroupShape,
        ) {
            optionList
                .map { option2string(it,resources) }
                .forEachIndexed { index, optionText ->
                    DropdownMenuItem(
                        text = { Text(optionText, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            checkedIndex = index
                            textFieldState.setTextAndPlaceCursorAtEnd(optionText)
                            expanded = false
                            selectedItem = optionList[index]
                            onOptionSelected.invoke(selectedItem!!)
                        },
                        selected = index == checkedIndex,
                        selectedLeadingIcon = {
                            Icon(
                                Icons.Default.Check,
                                modifier = Modifier.size(MenuDefaults.LeadingIconSize),
                                contentDescription = null,
                            )
                        },
                        shapes = MenuDefaults.itemShape(index, optionList.size),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun DropDownMenu3Preview() {

    val context = LocalContext.current
    val resources = LocalResources.current

    DropDownMenu(
        label = stringResource(R.string.drop_down_menu_label),
        optionList = Option.entries.toList(),
        preselectedOption = Option.SECOND,
        modifier = Modifier
            .padding(top = 20.dp)
            .background(Color.Yellow, shape = RoundedCornerShape(5.dp))
            .padding(top = 48.dp)
            .fillMaxWidth()
        ,
        onOptionSelected = {
            Toast.makeText(context, Option.option2string(it,resources),Toast.LENGTH_SHORT).show()
        },
        option2string = Option.option2string
    )
}

enum class Option {
    FIRST,
    SECOND,
    THIRD;

    companion object {
        val option2string = fun(option: Option, resources: Resources): String = when(option){
            FIRST -> resources.getString(R.string.option_first)
            SECOND -> resources.getString(R.string.option_second)
            THIRD -> resources.getString(R.string.option_third)
        }
    }
}