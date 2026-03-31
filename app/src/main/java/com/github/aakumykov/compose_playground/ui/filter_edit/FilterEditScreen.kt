package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.extensions.showToast
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber
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
                },
                onRuleClicked = {}
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
        errorMessage = null,
        onRuleClicked = {}
    )
}