package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Rule

interface RuleRepository {
    suspend fun add(rule: Rule)
    suspend fun update(rule: Rule)
    suspend fun list(filterId: String): List<Rule>
    suspend fun get(ruleId: String): Rule?
}