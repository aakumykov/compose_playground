package com.github.aakumykov.compose_playground.ui.rule_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.GlobalValues
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.model.RuleOperation
import com.github.aakumykov.compose_playground.model.RuleSubject
import com.github.aakumykov.compose_playground.ui.common.DropDownMenu
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
            uiState = (uiState as RuleUIState.Edit),
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


@Composable
fun RuleEditForm(
    uiState: RuleUIState.Edit,
    modifier: Modifier = Modifier,
    onSaveClicked: (editState: RuleUIState.Edit) -> Unit,
    onCancelClicked: () -> Unit,
) {
    var subject: RuleSubject? by remember { mutableStateOf(uiState.ruleSubject) }
    var operation: RuleOperation? by remember { mutableStateOf(uiState.ruleOperation) }
    var checkPattern: String? by remember { mutableStateOf(uiState.checkPattern) }

    Column(modifier = modifier.fillMaxWidth()) {
        DropDownMenu(
            label = stringResource(R.string.label_rule_subject),
            optionList = RuleSubject.entries.toList(),
            preselectedOption = uiState.ruleSubject,
            onOptionSelected = { subject = it },
            option2string = RuleSubject.enum2string
        )
        DropDownMenu(
            label = stringResource(R.string.label_rule_operation),
            optionList = RuleOperation.entries.toList(),
            preselectedOption = uiState.ruleOperation,
            onOptionSelected = { operation = it },
            option2string = RuleOperation.enum2string
        )

        OutlinedTextField(
            value = checkPattern ?: GlobalValues.EMPTY_STRING,
            onValueChange = { checkPattern = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.label_rule_check_pattern)) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onSaveClicked.invoke(
                    RuleUIState.Edit(
                        ruleOperation = operation,
                        ruleSubject = subject,
                        checkPattern = checkPattern,
                        created = uiState.created
                    )
                )
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