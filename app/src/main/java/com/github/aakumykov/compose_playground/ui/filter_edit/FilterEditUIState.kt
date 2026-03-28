package com.github.aakumykov.compose_playground.ui.filter_edit

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.FilterMode

sealed interface FilterEditUIState {

    object Loading: FilterEditUIState

    data class Error(val throwable: Throwable): FilterEditUIState

    data class Normal(
        val id: String?,
        val packageName: String,
        val mode: FilterMode,
        val enabled: Boolean,
        val errorMsg: String? = null

    ): FilterEditUIState {
        constructor(filter: Filter) : this(
            id = filter.id,
            packageName = filter.packageName,
            mode = filter.mode,
            enabled = filter.enabled,
        )
    }
}
