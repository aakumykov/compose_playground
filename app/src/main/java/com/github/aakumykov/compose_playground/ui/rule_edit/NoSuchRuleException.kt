package com.github.aakumykov.compose_playground.ui.rule_edit

class NoSuchRuleException(val ruleId: String): Exception() {
    override val message: String
        get() = "Where is no rule with id='${ruleId}'"
}