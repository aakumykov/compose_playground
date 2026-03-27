package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber

@Composable
fun FilterListScreen(
    onItemClicked: (filterId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterListViewModel = hiltViewModel(),
) {
    val uiState: FilterListUIState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is FilterListUIState.Success -> {
            FilterListScreen(
                (uiState as FilterListUIState.Success).list,
                onItemClicked = onItemClicked,
                onAddClicked = { viewModel.addFilter(Filter.createRandom()) },
                onClearClicked = { viewModel.removeAllFilters() },
                modifier = modifier
            )
        }

        is FilterListUIState.Loading -> {
            FilterListLoadingScreen(
//            modifier = modifier.fillMaxSize()
            )
        }

        else -> {
            FilterListErrorScreen(
                (uiState as FilterListUIState.Error).throwable,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun FilterListScreen(
    list: List<Filter>,
    onItemClicked: (filterId: String) -> Unit,
    onAddClicked: () -> Unit,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(items = list, key = { it.id }) { filter ->
                Text(
                    text = filter.packageName,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                        .clickable {
                            onItemClicked.invoke(filter.id)
                        }
                )
                HorizontalDivider()
            }
        }
        FloatingActionButton(
            onClick = onAddClicked,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon (
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = stringResource(R.string.description_filter_add_button)
            )
        }
        FloatingActionButton(
            onClick = onClearClicked,
            shape = FloatingActionButtonDefaults.smallShape,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon (
                painter = painterResource(R.drawable.outline_clear_all_24),
                contentDescription = stringResource(R.string.description_filter_add_button)
            )
        }
    }
}

/*@Preview(showSystemUi = false, showBackground = true)
@Composable
fun FilterListScreenPreview() {
    FilterListScreen(
        list = Filter.fakeList(),
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
    FilterListLoadingScreen(
//        modifier = Modifier.fillMaxWidth()
    )
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