package com.github.aakumykov.compose_playground.ui.filter_edit

import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.utils.newRandomId

sealed interface FilterUIState {

    object Loading: FilterUIState

    data class Error(val throwable: Throwable): FilterUIState

    data class Edit(
        val id: String?,
        val packageName: String,
        val mode: FilterMode?,
        val enabled: Boolean,
        val rules: List<Rule>? = emptyList()

    ): FilterUIState {

        companion object {
            fun fromExistingState(
                state: FilterUIState.Edit,
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

            fun asCreate(
                packageName: String,
                mode: FilterMode,
                enabled: Boolean
            ): Edit {
                return Edit(
                    id = newRandomId,
                    packageName = packageName,
                    mode = mode,
                    enabled = enabled
                )
            }

            fun asCreate(filterMetadata: FilterMetadata): Edit {
                return Edit(
                    id = filterMetadata.id,
                    packageName = filterMetadata.packageName,
                    mode = filterMetadata.mode,
                    enabled = filterMetadata.enabled
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
