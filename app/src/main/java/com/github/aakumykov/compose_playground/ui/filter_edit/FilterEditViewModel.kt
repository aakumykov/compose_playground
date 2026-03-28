package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.repository.FilterRepository
import com.github.aakumykov.compose_playground.ui.filter_list.FilterListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.collections.map
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val filterStates
        = mutableMapOf<String?, StateFlow<FilterEditUIState>>()

    fun getFilterAsStateFlow(filterId: String?): StateFlow<FilterEditUIState> {

        return filterStates.getOrPut(filterId) {
            flow {
                val result = try {
                    when (filterId) {
                        null -> FilterEditUIState.Error(NoSuchFilterException(null))
                        else -> filterRepository.get(filterId)
                            ?.let { FilterEditUIState.Success(it) }
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


    /*@OptIn(ExperimentalCoroutinesApi::class)
    fun getFilterAsStateFlow(filterId: String?): StateFlow<FilterEditUIState> {
        return flowOf(filterId)
            .map {
                if (null != it) filterRepository.get(it)
                else null
            }
            .map {
                if (null != it) FilterEditUIState.Success(it)
                else FilterEditUIState.Error(NoSuchFilterException(filterId))
            }
            .catch { emit(FilterEditUIState.Error(it)) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FilterEditUIState.Loading,
            )
    }*/
}