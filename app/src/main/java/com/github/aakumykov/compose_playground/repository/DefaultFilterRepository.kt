package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.data.model.Filter
import com.github.aakumykov.compose_playground.room.FilterDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFilterRepository @Inject constructor(
    private val filterDAO: FilterDAO
): FilterRepository {

    override val filters: Flow<List<Filter>> = filterDAO.list()

    override suspend fun add(filter: Filter) {
        filterDAO.add(filter)
    }
}