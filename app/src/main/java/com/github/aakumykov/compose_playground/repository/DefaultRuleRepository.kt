package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.repository.di.DispatcherType
import com.github.aakumykov.compose_playground.room.di.RuleDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DefaultRuleRepository @Inject constructor(
    @Named(DispatcherType.IO)
    private val dispatcher: CoroutineDispatcher,
    private val ruleDAO: RuleDAO
): RuleRepository {

    override suspend fun add(rule: Rule) = withContext(dispatcher) {
        ruleDAO.add(rule)
    }

    override suspend fun update(rule: Rule) = withContext(dispatcher) {
        ruleDAO.update(rule)
    }

    override fun listAsFlow(filterId: String): Flow<List<Rule>> {
        return ruleDAO.listAsFlow(filterId)
    }

    override suspend fun get(id: String): Rule? = withContext(dispatcher) {
        ruleDAO.get(id)
    }
}