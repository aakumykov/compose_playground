package com.github.aakumykov.compose_playground.ui.rule_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber
import kotlinx.coroutines.launch

@Composable
fun RuleEditScreen(
    ruleId: String?,
    filterId: String,
    onRuleSaved: (rule: Rule) -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RuleEditVideModel
) {
    LaunchedEffect(Unit) {
        if (null != ruleId) viewModel.startWorkForEdit(ruleId, filterId)
        else viewModel.startWorkForCreate(filterId)
    }

    val uiState: RuleUIState by viewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    when(uiState) {
        is RuleUIState.Edit -> RuleEditForm(
            editState = (uiState as RuleUIState.Edit),
            onSaveClicked = { editState: RuleUIState.Edit ->
                scope.launch {
                    viewModel.createOrUpdate(editState).also {
                        onRuleSaved.invoke(it)
                    }
                }
            },
            onCancelClicked = onCancelClicked,
            modifier = modifier
        )

        is RuleUIState.Loading -> LoadingThrobber(modifier = modifier)

        is RuleUIState.Error -> ErrorText(
            text = (uiState as RuleUIState.Error).throwable.errorMsgExtended,
            modifier = modifier
        )
    }
}


