package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.ui.common.DropDownMenu
import com.github.aakumykov.compose_playground.ui.common.EditFormTitle
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.theme.Danger
import com.github.aakumykov.compose_playground.utils.randomBool

@Composable
fun FilterEditForm(
    state: FilterUIState.Edit,
    rules: List<Rule>,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onAddRuleClicked: () -> Unit,
    onRuleClicked: (rule:Rule) -> Unit,
    onSaveClicked: (filterMode: FilterMode?, enabled: Boolean) -> Unit,
    onCancelClicked: () -> Unit,
    onDeleteClicked: (filterId: String) -> Unit,
) {
    var filterMode: FilterMode? by remember { mutableStateOf(state.mode) }
    var enabled : Boolean by rememberSaveable { mutableStateOf(state.enabled) }

    Column(modifier = modifier) {

        EditFormTitle(
            title = state.packageName,
            modifier = Modifier.padding(8.dp)
        )

        DropDownMenu(
            label = stringResource(R.string.label_filter_mode),
            optionList = FilterMode.entries.toList(),
            option2string = FilterMode.enum2string,
            preselectedOption = state.mode,
            modifier = Modifier,
            onOptionSelected = { filterMode = it }
        )

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Switch(
                checked = enabled,
                onCheckedChange = { enabled = it },
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = if (enabled) stringResource(R.string.label_filter_enabled_yes)
                else stringResource(R.string.label_filter_enabled_no),
                modifier = Modifier.clickable {
                    enabled = !enabled
                }
            )
        }

        if (null != errorMessage) {
            ErrorText(
                errorMessage,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        RuleList(
            rules = rules,
            modifier = Modifier.weight(1f, true),
            onAddRuleClicked = onAddRuleClicked,
            onRuleClicked = onRuleClicked,
        )

        Button(
            onClick = {
                onSaveClicked.invoke(filterMode, enabled)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) { Text(stringResource(R.string.button_save_filter)) }

        Button(
            onClick = onCancelClicked,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.button_cancel))
        }

        if (null != state.id) {
            Button(
                onClick = { onDeleteClicked.invoke(state.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Danger
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.button_delete_filter))
            }
        }
    }
}


@Composable
fun RuleList(
    rules: List<Rule>,
    modifier: Modifier = Modifier,
    onAddRuleClicked: () -> Unit,
    onRuleClicked: (rule:Rule) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (rules.isEmpty()) {
            RuleAddButton(
                onClick = onAddRuleClicked,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(items = rules, key = { it.id }) { rule ->
                    RuleListItem(
                        rule,
                        verticalPadding = 6.dp,
                        onClick = onRuleClicked
                    )
                    HorizontalDivider()
                }
            }

            RuleAddButton(
                onClick = onAddRuleClicked,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun RuleAddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.filter_edit_add_rulle_button_tex),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = stringResource(R.string.description_filter_add_button)
            )
        }
    }
}


@Preview(device = "spec:width=400dp,height=600dp,dpi=240")
@Composable
fun FilterEditFormPreview() {
    val uiState = Filter.random().let { filter ->
        FilterUIState.Edit(
            id = filter.id,
            packageName = filter.packageName,
            mode = filter.mode,
            enabled = randomBool,
            rules = Rule.randomList(filter.id)
        )
    }
    FilterEditForm(
        uiState,
        rules = Rule.randomList(uiState.id!!),
        errorMessage = null,
        onSaveClicked = { _,_ -> },
        onAddRuleClicked = {},
        onCancelClicked = {},
        onRuleClicked = {},
        onDeleteClicked = {}
    )
}