package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Rule
import kotlinx.coroutines.flow.Flow

interface RuleRepository {
    suspend fun add(rule: Rule)
    suspend fun update(rule: Rule)
    fun listAsFlow(filterId: String): Flow<List<Rule>>
    suspend fun get(id: String): Rule?
}