package com.github.aakumykov.compose_playground.ui.filter_edit

import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.utils.newRandomId

sealed interface FilterEditUIState {

    object Loading: FilterEditUIState

    data class Error(val throwable: Throwable): FilterEditUIState

    data class Edit(
        val id: String?,
        val packageName: String,
        val mode: FilterMode?,
        val enabled: Boolean,

    ): FilterEditUIState {

        companion object {
            fun asCreate(packageName: String): Edit {
                return Edit(
                    id = null,
                    packageName = packageName,
                    mode = null,
                    enabled = false
                )
            }
            fun asEdit(filter: Filter): Edit {
                return Edit(
                    id = filter.id,
                    packageName = filter.packageName,
                    mode = filter.mode,
                    enabled = filter.enabled
                )
            }
        }
    }
}
