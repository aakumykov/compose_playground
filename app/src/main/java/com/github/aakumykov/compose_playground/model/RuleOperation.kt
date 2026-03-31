package com.github.aakumykov.compose_playground.model

import android.content.res.Resources
import com.github.aakumykov.compose_playground.R

enum class RuleOperation {
    STARTS_WITH,
    ENDS_WITH,
    CONTAINS,
    EQUALS,
    REGEXP;

    companion object {
        val enum2string = fun(value: RuleOperation, resources: Resources): String = when(value){
             STARTS_WITH -> resources.getString(R.string.rule_operation_name_starts_with)
             ENDS_WITH -> resources.getString(R.string.rule_operation_name_ends_with)
             CONTAINS -> resources.getString(R.string.rule_operation_name_contains)
             EQUALS -> resources.getString(R.string.rule_operation_name_equals)
             REGEXP -> resources.getString(R.string.rule_operation_name_regexp)
        }
    }
}

fun RuleOperation.toHumanName(resources: Resources): String {
    return RuleOperation.enum2string.invoke(this, resources)
}

fun RuleOperation.toSymbols(): String = when(this) {
        RuleOperation.CONTAINS -> "*="
        RuleOperation.STARTS_WITH -> "^="
        RuleOperation.ENDS_WITH -> "=$"
        RuleOperation.EQUALS -> "=="
        RuleOperation.REGEXP -> "REGEXP"
}