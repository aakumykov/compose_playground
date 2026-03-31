package com.github.aakumykov.compose_playground.ui.rule_edit

import androidx.annotation.StringRes
import com.github.aakumykov.compose_playground.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.aakumykov.compose_playground.GlobalValues
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.model.RuleOperation
import com.github.aakumykov.compose_playground.model.RuleSubject
import com.github.aakumykov.compose_playground.ui.common.DropDownMenu
import com.github.aakumykov.compose_playground.ui.common.EditFormTitle
import com.github.aakumykov.compose_playground.utils.newRandomId

@Composable
fun RuleEditForm(
    editState: RuleUIState.Edit,
    modifier: Modifier = Modifier,
    onSaveClicked: (editState: RuleUIState.Edit) -> Unit,
    onCancelClicked: () -> Unit,
) {
    var subject: RuleSubject? by remember { mutableStateOf(editState.ruleSubject) }
    var operation: RuleOperation? by remember { mutableStateOf(editState.ruleOperation) }
    var checkPattern: String? by remember { mutableStateOf(editState.checkPattern) }

    Column(modifier = modifier.fillMaxWidth()) {

        @Composable
        @StringRes
        fun creationOrEditionPageTitle(editState: RuleUIState.Edit): String {
            return stringResource(
                if (null != editState.created) R.string.page_title_rule_edit
                else R.string.page_title_rule_create
            )
        }

        EditFormTitle(
            title = creationOrEditionPageTitle(editState),
            modifier = Modifier.padding(8.dp)
        )

        DropDownMenu(
            label = stringResource(R.string.label_rule_subject),
            optionList = RuleSubject.entries.toList(),
            preselectedOption = editState.ruleSubject ?: RuleSubject.TITLE,
            onOptionSelected = { subject = it },
            option2string = RuleSubject.enum2string
        )

        DropDownMenu(
            label = stringResource(R.string.label_rule_operation),
            optionList = RuleOperation.entries.toList(),
            preselectedOption = editState.ruleOperation ?: RuleOperation.CONTAINS,
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
                        created = editState.created
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(stringResource(R.string.button_save)) }

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
    }
}


@Preview(device = "spec:width=400dp,height=600dp,dpi=240")
@Composable
fun RuleEditFormPreview() {
    RuleEditForm(
        editState = RuleUIState.Edit.forEdit(Rule.random(newRandomId)),
        modifier = Modifier,
        onSaveClicked = {},
        onCancelClicked = {},
    )
}


/*
@StringRes
fun creationOrEditionPageTitle(editState: RuleUIState.Edit): Int {
    return if (null != editState.created) R.string.page_title_rule_create
    else R.string.page_title_rule_edit
}*/
