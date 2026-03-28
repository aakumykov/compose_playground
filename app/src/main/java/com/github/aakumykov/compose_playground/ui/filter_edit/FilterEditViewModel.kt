package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.repository.FilterRepository
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    private var currentFilter: Filter? = null
    private val cachedFilterState = mutableMapOf<String?, StateFlow<FilterEditUIState>>()

    fun getFilterAsStateFlow(filterId: String?): StateFlow<FilterEditUIState> {
        return cachedFilterState.getOrPut(filterId) {
            flow {
                val result = try {
                    when (filterId) {
                        null -> FilterEditUIState.Error(NoSuchFilterException(null))
                        else -> filterRepository.get(filterId)
                            ?.let {
                                currentFilter = it
                                FilterEditUIState.Normal(it)
                            }
                            ?: FilterEditUIState.Error(NoSuchFilterException(filterId))
                    }
                } catch (e: Exception) {
                    FilterEditUIState.Error(e)
                }
                emit(result)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FilterEditUIState.Loading,
            )
        }
    }


    fun saveFilter(packageName: String, filterMode: FilterMode, isEnabled: Boolean) {
        viewModelScope.launch {
            try {
                if (null != currentFilter) updateFilter(currentFilter!!, filterMode, isEnabled)
                else createFilter(packageName, filterMode, isEnabled)
            } catch (t: Throwable) {

            }
        }
    }

    private suspend fun updateFilter(
        existingFilter: Filter,
        filterMode: FilterMode,
        enabled: Boolean
    ) {
        Filter(
            id = existingFilter.id,
            packageName = existingFilter.packageName,
            mode = filterMode,
            modified = currentTimestamp,
            enabled = enabled
        ).also {
            filterRepository.update(it)
        }
    }

    private suspend fun createFilter(
        packageName: String,
        filterMode: FilterMode,
        enabled: Boolean
    ) {
        Filter.create(
            packageName = packageName,
            mode = filterMode,
            isEnabled = enabled
        ).also {
            filterRepository.add(it)
        }
    }
}