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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FilterListViewModel @Inject constructor(
    private val filterRepository: FilterRepository
): ViewModel() {

    val uiState: StateFlow<FilterListUIState> = filterRepository
        .filters
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