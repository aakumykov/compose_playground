package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    fun getUiStateFor(filterId: String?): StateFlow<FilterEditUIState> {
        return flow {

            filterRepository.get(filterId)?.let {
                emit(FilterEditUIState.Success(it))
            } ?: run {
                emit(FilterEditUIState.Error(NoSuchFilterException(filterId)))
            }

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FilterEditUIState.Loading)
    }
}