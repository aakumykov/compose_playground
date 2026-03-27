package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.data.model.Filter
import com.github.aakumykov.compose_playground.repository.di.DispatcherType
import com.github.aakumykov.compose_playground.room.FilterDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DefaultFilterRepository @Inject constructor(
    @Named("qwerty")
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val filterDAO: FilterDAO
): FilterRepository {

    override val filters: Flow<List<Filter>> get() {
        return Filter.fakeListFlow()
    }

    override fun listAsFlow(): Flow<List<Filter>> {
        return filterDAO.listAsFlow()
    }

    override suspend fun add(filter: Filter) = withContext(dispatcher) {
        filterDAO.add(filter)
    }

    override suspend fun removeAllFilters() = withContext(dispatcher) {
        filterDAO.deleteAll()
    }
}