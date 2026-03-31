package com.github.aakumykov.compose_playground.model

import android.content.res.Resources
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.FilterMode.BLACK
import com.github.aakumykov.compose_playground.model.FilterMode.WHITE

enum class RuleSubject {
    TITLE,
    MESSAGE,
    ANY,
    BOTH;

    companion object {
        val enum2string = fun(value: RuleSubject, resources: Resources): String = when(value){
            RuleSubject.TITLE -> resources.getString(R.string.rule_subject_name_title)
            RuleSubject.MESSAGE -> resources.getString(R.string.rule_subject_name_message)
            RuleSubject.ANY -> resources.getString(R.string.rule_subject_name_any)
            RuleSubject.BOTH -> resources.getString(R.string.rule_subject_name_both)
        }
    }
}

fun RuleSubject.toHumanName(resources: Resources): String {
    return RuleSubject.enum2string.invoke(this, resources)
}