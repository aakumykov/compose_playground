package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.map

@HiltViewModel
class FilterListViewModel @Inject constructor(
    private val filterRepository: FilterRepository
): ViewModel() {

    val uiState: StateFlow<FilterListUIState> =
        flowOf(
            listOf(
                "cb19ed80-48e9-44d4-9f4e-f274718b8647",
                "b2ba41c3-0a4a-43d7-8461-13a457628649",
                "a85d18b8-2b80-4f46-af45-d1b5b219d4c8",
                "ec39e493-07f6-40dd-9f30-87e906a35753",
                "4109435b-a748-45a6-99a9-f4d04de98e87",
            )
        )
        .map { filterIdList ->
            filterIdList.mapNotNull { filterId ->
                filterRepository.get(filterId)
            }
        }
        .map<List<Filter>,FilterListUIState> { FilterListUIState.Success(it) }
        .catch { emit(FilterListUIState.Error(it)) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FilterListUIState.Loading
        )


    fun addFilter(filter: Filter) = viewModelScope.launch {
        filterRepository.add(filter)
    }


    fun removeAllFilters() = viewModelScope.launch {
        filterRepository.removeAllFilters()
    }
}