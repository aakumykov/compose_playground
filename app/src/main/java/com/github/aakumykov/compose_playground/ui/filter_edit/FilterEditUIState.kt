package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.ui.util.packInts
import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule

sealed interface FilterEditUIState {

    object Loading: FilterEditUIState

    data class Error(val throwable: Throwable): FilterEditUIState

    data class Edit(
        val id: String?,
        val packageName: String,
        val mode: FilterMode?,
        val enabled: Boolean,
        val rules: List<Rule>? = emptyList()

    ): FilterEditUIState {

        companion object {
            fun fromExistingState(
                state: FilterEditUIState.Edit,
                additionalRules: List<Rule>
            ): Edit {
                return Edit(
                    id = state.id,
                    packageName = state.packageName,
                    mode = state.mode,
                    enabled = state.enabled,
                    rules = buildList {
                        state.rules?.forEach { add(it) }
                        additionalRules.forEach { add(it) }
                    }
                )
            }
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
