package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
        return filterRepository.get(filterId).map {
            if (null != it) FilterEditUIState.Success(it)
            else FilterEditUIState.Error(NoSuchFilterException(filterId))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FilterEditUIState.Loading
        )
    }
}