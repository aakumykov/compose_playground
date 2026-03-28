package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.ui.common.DropDownMenu
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber
import kotlinx.coroutines.launch

@Composable
fun FilterEditScreen(
    filterId: String?,
    packageName: String?,
    onFilterSaved: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterEditViewModel
) {
    val uiState: FilterEditUIState by viewModel.uiState.collectAsStateWithLifecycle()

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

    val scope = rememberCoroutineScope()

    when(uiState) {

        is FilterEditUIState.Edit -> {
            FilterEditForm(
                state = uiState as FilterEditUIState.Edit,
                modifier = modifier,
                onSaveClicked = { filterMode: FilterMode?, isEnabled: Boolean? ->
                    scope.launch {
                        viewModel.createOfUpdateFilter(filterMode, isEnabled)
                    }
                },
                onCancelClicked = onCancelClicked,
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
    modifier: Modifier = Modifier,
    onSaveClicked: (filterMode: FilterMode?, enabled: Boolean) -> Unit,
    onCancelClicked: () -> Unit,
) {
    var filterMode: FilterMode? by remember { mutableStateOf(state.mode) }
    var enabled : Boolean by rememberSaveable { mutableStateOf(state.enabled) }

    Column(modifier = modifier) {

        Text(
            text = state.packageName,
            textAlign = TextAlign.Center,
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
                onCheckedChange = { enabled = it }
            )
            Text(
                if(enabled) stringResource(R.string.label_filter_enabled_yes)
                else stringResource(R.string.label_filter_enabled_no)
            )
        }

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
    }
}


/*
@Preview(showSystemUi = true)
@Composable
fun FilterEditScreenPreview() {
    FilterEditForm(
        state = FilterEditUIState.Edit(Filter.random),
        onSaveClicked = { mode: FilterMode, isEnabled: Boolean -> },
        onCancelClicked = {},
        modifier = Modifier.padding(top = 50.dp)
    )
}
*/
