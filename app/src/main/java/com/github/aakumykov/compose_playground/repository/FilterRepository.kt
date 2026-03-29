package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.SomeFilter
import com.github.aakumykov.compose_playground.ui.filter_edit.FilterEditUIState
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    val filters: Flow<List<SomeFilter>>
    suspend fun removeAllFilters()

    suspend fun add(filter: SomeFilter)
    suspend fun update(filter: SomeFilter)
    suspend fun get(filterId: String?): SomeFilter?
    suspend fun delete(filterId: String)
}