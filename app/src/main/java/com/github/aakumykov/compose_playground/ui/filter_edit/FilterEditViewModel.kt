package com.github.aakumykov.compose_playground.ui.filter_edit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.extensions.errorMsg
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.SomeFilter
import com.github.aakumykov.compose_playground.repository.FilterRepository
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FilterEditUIState> = MutableStateFlow(FilterEditUIState.Loading)
    val uiState: StateFlow<FilterEditUIState> = _uiState

    private val _isCompleteState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCompleteState: StateFlow<Boolean> = _isCompleteState

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var mCurrentFilterMetadata: FilterMetadata? = null
    private var currantPackageName: String? = null

    suspend fun startWorkForCreate(packageName: String) {
        currantPackageName = packageName
        _uiState.emit(FilterEditUIState.Edit.asCreate(packageName))
    }

    suspend fun startWorkForEdit(filterId: String) {
        filterRepository.get(filterId).also { filter: SomeFilter? ->
            _uiState.emit(
                if (null != filter) {
                    mCurrentFilterMetadata = filter.filterMetadata
                    FilterEditUIState.Edit.asEdit(filter.filterMetadata)
                }
                else FilterEditUIState.Error(NoSuchFilterException(filterId))
            )
        }
    }

    suspend fun createOfUpdateFilter(newFilterMode: FilterMode?, isEnabled: Boolean?) {
        try {
            if (null != mCurrentFilterMetadata) {
                filterRepository.update(
                    SomeFilter.fromFilter(FilterMetadata(
                        id = mCurrentFilterMetadata!!.id,
                        packageName = mCurrentFilterMetadata!!.packageName,
                        mode = newFilterMode!!,
                        enabled = isEnabled!!,
                        modified = currentTimestamp
                    ))
                )
            } else {
                filterRepository.add(
                    SomeFilter.fromFilter(FilterMetadata.create(
                        packageName = currantPackageName!!,
                        mode = newFilterMode!!,
                        isEnabled = isEnabled!!
                    ))
                )
            }

            _isCompleteState.emit(true)
        }
        catch (t: Throwable) {
            _errorMessage.emit(t.errorMsgExtended)
            Log.d(TAG, t.errorMsg, t)
        }
    }

    suspend fun showError(exception: Exception) {
        _uiState.emit(FilterEditUIState.Error(exception))
    }

    suspend fun deleteFilter(filterId: String) {
        filterRepository.delete(filterId)
        _isCompleteState.emit(true)
    }

    companion object {
        val TAG: String = FilterEditViewModel::class.java.simpleName
    }
}