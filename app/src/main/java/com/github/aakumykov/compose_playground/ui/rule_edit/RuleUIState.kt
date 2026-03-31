package com.github.aakumykov.compose_playground.ui.rule_edit

import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.model.RuleOperation
import com.github.aakumykov.compose_playground.model.RuleSubject

sealed interface RuleUIState {

    data class Edit(
        val ruleOperation: RuleOperation?,
        val ruleSubject: RuleSubject?,
        val checkPattern: String?,
        val created: Long?,
    ): RuleUIState {
        companion object {
            fun forEdit(rule: Rule): Edit {
                return Edit(
                    ruleOperation = rule.ruleOperation,
                    ruleSubject = rule.ruleSubject,
                    checkPattern = rule.checkPattern,
                    created = rule.created
                )
            }

            fun forCreate(): Edit {
                return Edit(
                    ruleOperation = null,
                    ruleSubject = null,
                    checkPattern = null,
                    created = null
                )
            }
        }
    }

    data object Loading: RuleUIState

    data class Error(val throwable: Throwable): RuleUIState
}