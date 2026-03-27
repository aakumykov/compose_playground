package com.github.aakumykov.compose_playground.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.aakumykov.compose_playground.ui.filter_list.FilterListScreen

@Composable
fun MainNavigation() {

    val backStack = rememberNavBackStack(FilterList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<FilterList> {
                FilterListScreen(
                    onItemClicked = { filterId ->

                    },
                    modifier = Modifier.safeDrawingPadding().padding(16.dp)
                )
            }
        }
    )
}