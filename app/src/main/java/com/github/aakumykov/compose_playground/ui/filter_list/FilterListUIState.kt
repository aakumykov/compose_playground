package com.github.aakumykov.compose_playground.ui.filter_list

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.SomeFilter

sealed interface FilterListUIState {
    object Loading: FilterListUIState
    data class Success(val list: List<SomeFilter>): FilterListUIState
    data class Error(val throwable: Throwable): FilterListUIState
}