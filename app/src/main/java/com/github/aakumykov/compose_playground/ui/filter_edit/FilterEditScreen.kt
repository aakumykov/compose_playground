package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.extensions.showToast
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.ui.common.DropDownMenu
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber
import com.github.aakumykov.compose_playground.ui.theme.Danger
import kotlinx.coroutines.launch

@Composable
fun FilterEditScreen(
    filterId: String?,
    packageName: String?,
    onAddRuleClicked: (filterId: String) -> Unit,
    onRuleClicked: (ruleId: String, filterId: String) -> Unit,
    onFilterSaved: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterEditViewModel
) {
    val uiState: FilterEditUIState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorMessage: String? by viewModel.errorMessage.collectAsStateWithLifecycle()
    var rules: List<Rule> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        if (null != filterId) viewModel.startWorkForEdit(filterId)
        else if (null != packageName) viewModel.startWorkForCreate(packageName)
        else viewModel.showError(IllegalArgumentException("You must supply filterId or packageName"))
    }

    LaunchedEffect(Unit) {
        viewModel.isCompleteState.collect { isComplete ->
            if (isComplete) {
                onFilterSaved.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.rules.collect {
            rules = it
        }
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    when(uiState) {

        is FilterEditUIState.Edit -> {
            FilterEditForm(
                state = uiState as FilterEditUIState.Edit,
                rules = rules,
                errorMessage = errorMessage,
                modifier = modifier,
                onAddRuleClicked = {
                    if (null != filterId) onAddRuleClicked.invoke(filterId)
                    else context.showToast("filterId is null")
                },
                onSaveClicked = { filterMode: FilterMode?, isEnabled: Boolean? ->
                    scope.launch {
                        viewModel.createOfUpdateFilter(filterMode, isEnabled)
                    }
                },
                onCancelClicked = onCancelClicked,
                onDeleteClicked = { filterId ->
                    scope.launch {
                        viewModel.deleteFilter(filterId)
                    }
                }
            )
        }

        is FilterEditUIState.Error -> {
            ErrorText(
                text = (uiState as FilterEditUIState.Error).throwable.errorMsgExtended,
                modifier = modifier
            )
        }

        is FilterEditUIState.Loading -> {
            LoadingThrobber(modifier = modifier)
        }
    }
}


@Composable
fun FilterEditForm(
    state: FilterEditUIState.Edit,
    rules: List<Rule>,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onAddRuleClicked: () -> Unit,
    onSaveClicked: (filterMode: FilterMode?, enabled: Boolean) -> Unit,
    onCancelClicked: () -> Unit,
    onDeleteClicked: (filterId: String) -> Unit,
) {
    var filterMode: FilterMode? by remember { mutableStateOf(state.mode) }
    var enabled : Boolean by rememberSaveable { mutableStateOf(state.enabled) }

    Column(modifier = modifier) {

        Text(
            text = state.packageName,
            textAlign = TextAlign.Center,
            fontWeight = Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        DropDownMenu(
            label = stringResource(R.string.label_filter_mode),
            optionList = FilterMode.entries.toList(),
            option2string = FilterMode.enum2string,
            preselectedOption = state.mode,
            modifier = Modifier,
            onOptionSelected = { filterMode = it}
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
                text = if(enabled) stringResource(R.string.label_filter_enabled_yes)
                else stringResource(R.string.label_filter_enabled_no),
                modifier = Modifier.clickable {
                    enabled = !enabled
                }
            )
        }

        if (null != errorMessage) {
            ErrorText(errorMessage,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5E5F1))
            .weight(1f, true)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = rules, key = { it.id }) {
                    Text(it.id)
                }
            }

            FloatingActionButton(
                onClick = onAddRuleClicked,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(20.dp)
            ) {
                Icon (
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = stringResource(R.string.description_filter_add_button)
                )
            }
        }

//        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onSaveClicked.invoke(filterMode, enabled)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(stringResource(R.string.button_save)) }

        Button(
            onClick = onCancelClicked,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary),
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
                Text(stringResource(R.string.button_delete))
            }
        }
    }
}



@Preview(showSystemUi = true,
    device = "spec:width=400dp,height=750dp,dpi=240")
@Composable
fun FilterEditScreenPreview() {
    FilterEditForm(
        state = FilterEditUIState.Edit.asCreate("packageName"),
        rules = emptyList(),
        onAddRuleClicked = {},
        onSaveClicked = { _: FilterMode?, _: Boolean -> },
        onCancelClicked = {},
        onDeleteClicked = { _ -> },
        errorMessage = null
    )
}