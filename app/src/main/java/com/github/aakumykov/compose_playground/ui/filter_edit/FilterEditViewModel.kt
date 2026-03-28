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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    init {
        println()
    }

    /*val uiState: StateFlow<FilterListUIState> = filterRepository
        .filters
        .map<List<Filter>,FilterListUIState> { FilterListUIState.Success(it) }
        .catch { emit(FilterListUIState.Error(it)) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FilterListUIState.Loading
        )*/

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFilterAsStateFlow(): StateFlow<FilterEditUIState> {
        val filterId = "4109435b-a748-45a6-99a9-f4d04de98e87"

        return listOf(filterId)
            .asFlow()
            .map {
                if (null != it) filterRepository.get(it)
                else null
            }
            .map {
                if (null != it) FilterEditUIState.Success(it)
                else FilterEditUIState.Error(NoSuchFilterException(filterId))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FilterEditUIState.Loading,
            )
    }



    /*val selectedNote: StateFlow<Filter?> = selectedNoteId
        .flatMapLatest {
            if (it == null) flowOf(null)
            else dao.getSingleNoteByID(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = null,
        )*/
}