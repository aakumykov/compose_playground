package com.github.aakumykov.compose_playground.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.aakumykov.compose_playground.ui.filter_edit.FilterEditScreen
import com.github.aakumykov.compose_playground.ui.filter_list.FilterListScreen
import com.github.aakumykov.compose_playground.utils.randomString

@Composable
fun MainNavigationNew() {

    val backStack = rememberNavBackStack(FilterList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<FilterList> {
                FilterListScreen(
                    onItemClicked = { filterId ->
                        backStack.add(FilterEdit.byFilterId(filterId))
                    },
                    onAddClicked = {
                        backStack.add(FilterEdit.byPackageName(randomString))
                    },
                    modifier = Modifier.safeDrawingPadding().padding(16.dp)
                )
            }

            entry<FilterEdit> {
                FilterEditScreen(
                    filterId = it.filterId,
                    packageName = it.packageName,
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
        }
    )
}