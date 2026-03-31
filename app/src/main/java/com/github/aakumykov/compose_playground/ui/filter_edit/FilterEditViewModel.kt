package com.github.aakumykov.compose_playground.ui.filter_edit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.aakumykov.compose_playground.extensions.errorMsg
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.FilterMode
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.repository.FilterRepository
import com.github.aakumykov.compose_playground.repository.RuleRepository
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FilterEditViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
    private val ruleRepository: RuleRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {


    private val _uiState: MutableStateFlow<FilterUIState>
        = MutableStateFlow(FilterUIState.Loading)
    val uiState: StateFlow<FilterUIState> = _uiState

    private val _rules: MutableStateFlow<List<Rule>> = MutableStateFlow(emptyList())
    val rules: StateFlow<List<Rule>> = _rules

    private val _isCompleteState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCompleteState: StateFlow<Boolean> = _isCompleteState

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var isCreation: Boolean = true

    private val currentEditState: FilterUIState.Edit
        get() = uiState.value as FilterUIState.Edit

    suspend fun startWorkForCreate(packageName: String) {
        isCreation = true
        _uiState.emit(FilterUIState.Edit.asCreate(
            packageName = packageName,
            mode = appPreferences.DEFAULT_FILTER_MODE,
            enabled = appPreferences.DEFAULT_FILTER_ENABLED
        ))
    }

    suspend fun startWorkForEdit(filterId: String) {
        isCreation = false
        filterRepository.get(filterId).also { filter: Filter? ->
            _uiState.emit(
                if (null != filter) {
                    FilterUIState.Edit.asEdit(filter.filterMetadata)
                } else {
                    FilterUIState.Error(NoSuchFilterException(filterId))
                }
            )
        }

        viewModelScope.launch {
            ruleRepository.listAsFlow(filterId).collect {
                _rules.emit(it)
            }
        }
    }

    suspend fun createOfUpdateFilter(
        newFilterMode: FilterMode?,
        isEnabled: Boolean?
    ) {
        try {
            if (isCreation) {
                filterRepository.add(
                    Filter.create(FilterMetadata.create(
                        packageName = currentEditState.packageName,
                        mode = newFilterMode!!,
                        isEnabled = isEnabled!!
                    ))
                )
            } else {
                filterRepository.update(
                    Filter.create(FilterMetadata(
                        id = currentEditState.id!!,
                        packageName = currentEditState.packageName,
                        mode = currentEditState.mode!!,
                        enabled = currentEditState.enabled,
                        modified = currentTimestamp
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
        _uiState.emit(FilterUIState.Error(exception))
    }

    suspend fun deleteFilter(filterId: String) {
        filterRepository.delete(filterId)
        _isCompleteState.emit(true)
    }

    companion object {
        val TAG: String = FilterEditViewModel::class.java.simpleName
    }
}