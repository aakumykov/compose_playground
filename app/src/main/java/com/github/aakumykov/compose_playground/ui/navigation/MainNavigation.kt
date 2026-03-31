package com.github.aakumykov.compose_playground.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.ui.rule_edit.RuleEditScreen
import com.github.aakumykov.compose_playground.ui.filter_edit.FilterEditScreen
import com.github.aakumykov.compose_playground.ui.filter_list.FilterListScreen
import com.github.aakumykov.compose_playground.utils.randomString

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(FilterListTarget)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            entry<FilterListTarget> {
                FilterListScreen(
                    onItemClicked = { filterId ->
                        backStack.add(FilterEditTarget.byFilterId(filterId))
                    },
                    onAddClicked = {
                        backStack.add(FilterEditTarget.byPackageName(randomString))
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .padding(12.dp)
                )
            }

            entry<FilterEditTarget> {
                FilterEditScreen(
                    filterId = it.filterId,
                    packageName = it.packageName,
                    onAddRuleClicked = { filterId: String ->
                        backStack.add(RuleEditTarget.forCreate(
                            filterId
                        ))
                    },
                    onRuleClicked = { rule: Rule ->
                        backStack.add(RuleEditTarget.forEdit(
                            ruleId = rule.id,
                            filterId = rule.filterId
                        ))
                    },
                    onFilterSaved = {
                        backStack.removeLastOrNull()
                    },
                    onCancelClicked = {
                        backStack.removeLastOrNull()
                    },
                    modifier = Modifier.safeDrawingPadding().padding(16.dp),
                    viewModel = hiltViewModel()
                )
            }

            entry<RuleEditTarget> {
                RuleEditScreen(
                    ruleId = it.ruleId,
                    filterId = it.filterId,
                    onRuleSaved = { backStack.removeLastOrNull() },
                    onCancelClicked = { backStack.removeLastOrNull() },
                    modifier = Modifier.safeDrawingPadding().padding(16.dp),
                    viewModel = hiltViewModel()
                )
            }
        },
        modifier = modifier
    )
}