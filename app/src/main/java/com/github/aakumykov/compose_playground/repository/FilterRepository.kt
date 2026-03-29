package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.ui.filter_edit.FilterEditUIState
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    val filters: Flow<List<Filter>>
    suspend fun removeAllFilters()

    suspend fun add(filter: Filter)
    suspend fun update(filter: Filter)
    suspend fun get(filterId: String?): Filter?
    suspend fun delete(filterId: String)
}