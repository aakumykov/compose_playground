package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber

@Composable
fun FilterListScreen(
    onItemClicked: (filterId: String) -> Unit,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterListViewModel = hiltViewModel(),
) {
    val uiState: FilterListUIState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is FilterListUIState.Success -> {
            FilterList(
                (uiState as FilterListUIState.Success).list,
                onItemClicked = onItemClicked,
                onAddClicked = onAddClicked,
                onClearClicked = { viewModel.removeAllFilters() },
                modifier = modifier
            )
        }

        is FilterListUIState.Loading -> {
            FilterListLoadingScreen()
        }

        else -> {
            FilterListErrorScreen(
                (uiState as FilterListUIState.Error).throwable,
                modifier = modifier,
            )
        }
    }
}

/*@Preview(showSystemUi = false, showBackground = true)
@Composable
fun FilterListScreenPreview() {
    FilterListScreen(
        list = FilterMetadata.fakeList(),
        onAddClicked = {},
    )
}*/


@Composable
fun FilterListLoadingScreen(modifier: Modifier = Modifier){
    LoadingThrobber(modifier = modifier)
}

@Preview(showSystemUi = false)
@Composable
fun FilterListLoadingScreenPreview() {
    FilterListLoadingScreen()
}


@Composable
fun FilterListErrorScreen(
    throwable: Throwable,
    modifier: Modifier = Modifier
) {
    ErrorText(
        throwable.errorMsgExtended,
        modifier = modifier.fillMaxWidth()
    )
}