package com.github.aakumykov.compose_playground.ui.filter_edit

import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode

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
            fun asEdit(filterMetadata: FilterMetadata): Edit {
                return Edit(
                    id = filterMetadata.id,
                    packageName = filterMetadata.packageName,
                    mode = filterMetadata.mode,
                    enabled = filterMetadata.enabled
                )
            }
        }
    }
}
