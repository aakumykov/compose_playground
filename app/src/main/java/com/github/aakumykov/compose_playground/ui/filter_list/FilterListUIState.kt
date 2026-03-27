package com.github.aakumykov.compose_playground.ui.filter_list

import com.github.aakumykov.compose_playground.model.Filter

sealed interface FilterListUIState {
    object Loading: FilterListUIState
    data class Success(val list: List<Filter>): FilterListUIState
    data class Error(val throwable: Throwable): FilterListUIState
}