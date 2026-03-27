package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.data.model.Filter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {

    val filters: Flow<List<Filter>>

    fun listAsFlow(): Flow<List<Filter>>

    suspend fun add(filter: Filter)
    suspend fun removeAllFilters()
}