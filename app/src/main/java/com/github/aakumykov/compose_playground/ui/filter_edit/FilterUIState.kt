package com.github.aakumykov.compose_playground.ui.filter_edit

import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import com.github.aakumykov.compose_playground.utils.newRandomId

sealed interface FilterUIState {

    object Loading: FilterUIState

    data class Error(val throwable: Throwable): FilterUIState

    data class Edit(
        val id: String?,
        val packageName: String,
        val mode: FilterMode?,
        val enabled: Boolean,
        val rules: List<Rule>? = emptyList(),
        val metadata: FilterMetadata

    ): FilterUIState {

        companion object {
            fun fromExistingState(
                state: FilterUIState.Edit,
                additionalRules: List<Rule>
            ): Edit {
                val metadata = FilterMetadata(
                    id = state.id!!,
                    packageName = state.packageName,
                    mode = state.mode!!,
                    enabled = state.enabled,
                    modified = currentTimestamp
                )
                return Edit(
                    id = metadata.id,
                    packageName = metadata.packageName,
                    mode = metadata.mode,
                    enabled = metadata.enabled,
                    rules = buildList {
                        state.rules?.forEach { add(it) }
                        additionalRules.forEach { add(it) }
                    },
                    metadata = metadata
                )
            }

            fun asCreate(
                packageName: String,
                mode: FilterMode,
                enabled: Boolean
            ): Edit {
                val metadata = FilterMetadata(
                    id = newRandomId,
                    packageName = packageName,
                    mode = mode,
                    enabled = enabled,
                    modified = currentTimestamp
                )
                return Edit(
                    id = metadata.id,
                    packageName = metadata.packageName,
                    mode = metadata.mode,
                    enabled = metadata.enabled,
                    metadata = metadata
                )
            }

/*            fun asCreate(filterMetadata: FilterMetadata): Edit {
                return Edit(
                    id = filterMetadata.id,
                    packageName = filterMetadata.packageName,
                    mode = filterMetadata.mode,
                    enabled = filterMetadata.enabled
                )
            }*/

            fun asEdit(filterMetadata: FilterMetadata): Edit {
                return Edit(
                    id = filterMetadata.id,
                    packageName = filterMetadata.packageName,
                    mode = filterMetadata.mode,
                    enabled = filterMetadata.enabled,
                    metadata = filterMetadata
                )
            }
        }
    }
}
