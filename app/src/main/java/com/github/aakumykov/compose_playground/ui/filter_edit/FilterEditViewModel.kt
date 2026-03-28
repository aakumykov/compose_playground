package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    //
    // С этим вариантом идёт жуткое зацикливание.
    //
    /*fun getUiStateFor(filterId: String?): StateFlow<FilterEditUIState> {
        return flow {

            filterRepository.get(filterId)?.let {
                emit(FilterEditUIState.Success(it))
            } ?: run {
                emit(FilterEditUIState.Error(NoSuchFilterException(filterId)))
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FilterEditUIState.Loading
        )
    }*/

    //
    // С этой хуйнёй так же. Будет интересней найти причину.
    //
    fun getUiStateFor(filterId: String?): StateFlow<FilterEditUIState> {
        return filterRepository.getAsFlow(filterId).map {
            if (null != it) FilterEditUIState.Success(it)
            else FilterEditUIState.Error(NoSuchFilterException(filterId))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FilterEditUIState.Loading
        )
    }

    suspend fun getFilter(filterId: String?): FilterEditUIState {
        return viewModelScope.async {
            val filter = filterRepository.get(filterId)
            if (null == filter) FilterEditUIState.Error(NoSuchFilterException(filterId))
            else FilterEditUIState.Success(filter)
        }.await()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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
            .map { it }
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