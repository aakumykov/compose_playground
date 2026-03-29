package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.SomeFilter
import com.github.aakumykov.compose_playground.repository.di.DispatcherType
import com.github.aakumykov.compose_playground.room.FilterDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DefaultFilterRepository @Inject constructor(
    @Named(DispatcherType.IO)
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val filterDAO: FilterDAO
): FilterRepository {

    override val filters: Flow<List<SomeFilter>> = filterDAO.list()

    override suspend fun add(filter: SomeFilter) = withContext(dispatcher) {
        filterDAO.add(filter.filter)
    }

    override suspend fun update(filter: SomeFilter) = withContext(dispatcher) {
        filterDAO.update(filter.filter)
    }

    override suspend fun removeAllFilters() = withContext(dispatcher) {
        filterDAO.deleteAll()
    }

    override suspend fun get(filterId: String?): SomeFilter? = withContext(dispatcher) {
        filterDAO.get(filterId)
    }

    override suspend fun delete(filterId: String) = withContext(dispatcher) {
        filterDAO.delete(filterId)
    }
}