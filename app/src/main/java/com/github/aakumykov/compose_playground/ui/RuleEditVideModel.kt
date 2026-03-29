package com.github.aakumykov.compose_playground.ui

import androidx.lifecycle.ViewModel
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.repository.RuleRepository
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import com.github.aakumykov.compose_playground.utils.newRandomId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RuleEditVideModel @Inject constructor(
    private val ruleRepository: RuleRepository,
): ViewModel() {

    private var currentRuleId: String? = null
    private var currentFilterId: String? = null

    private val _uiState: MutableStateFlow<RuleUIState> = MutableStateFlow(RuleUIState.Loading)
    val uiState: StateFlow<RuleUIState> = _uiState

    suspend fun startWorkForCreate(filterId: String) {
        currentFilterId = filterId
        _uiState.emit(RuleUIState.Edit.forCreate())
    }

    suspend fun startWorkForEdit(ruleId: String, filterId: String) {
        currentFilterId = filterId
        currentRuleId = ruleId

        ruleRepository.get(ruleId).also { rule ->
            if (null != rule) _uiState.emit(RuleUIState.Edit.forEdit(rule))
            else _uiState.emit(RuleUIState.Error(NoSuchRuleException(ruleId)))
        }
    }

    suspend fun createOrUpdate(editState: RuleUIState.Edit): Rule {
        val rule = Rule(
            id = currentRuleId ?: newRandomId,
            filterId = currentFilterId!!,
            ruleSubject = editState.ruleSubject!!,
            ruleOperation = editState.ruleOperation!!,
            checkPattern = editState.checkPattern!!,
            created = editState.created ?: currentTimestamp
        )
        if (null == currentRuleId) {
            ruleRepository.add(rule)
        } else {
            ruleRepository.update(rule)
        }
        return rule
    }
}