package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.repository.di.DispatcherType
import com.github.aakumykov.compose_playground.room.FilterDAO
import com.github.aakumykov.compose_playground.room.FilterMetadataDAO
import com.github.aakumykov.compose_playground.room.di.RuleDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DefaultFilterRepository @Inject constructor(
    @Named(DispatcherType.IO)
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val filterMetadataDAO: FilterMetadataDAO,
    private val filterDAO: FilterDAO,
    private val ruleDAO: RuleDAO,
)
    : FilterRepository
{
    override val filters: Flow<List<Filter>> = filterDAO.list()

    override suspend fun get(filterId: String?): Filter? = withContext(dispatcher) {
        filterDAO.get(filterId)
    }

    override suspend fun add(filter: Filter) = withContext(dispatcher) {
        filterMetadataDAO.add(filter.filterMetadata)
    }

    override suspend fun update(filter: Filter) = withContext(dispatcher) {
        filterMetadataDAO.update(filter.filterMetadata)
    }

    override suspend fun removeAllFilters() = withContext(dispatcher) {
        filterMetadataDAO.deleteAll()
    }

    override suspend fun delete(filterId: String) = withContext(dispatcher) {
        filterMetadataDAO.delete(filterId)
    }
}