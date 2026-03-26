package com.github.aakumykov.compose_playground.ui.filters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.github.aakumykov.compose_playground.data.model.Filter
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.model.FilterListUIState
import kotlinx.coroutines.launch

@Composable
fun FilterListScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterListViewModel = hiltViewModel(),
) {
    val uiState: FilterListUIState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is FilterListUIState.Success) {
        FilterListScreen(
            (uiState as FilterListUIState.Success).list,
            onAddClicked = {
                viewModel.addFilter(Filter.createRandom())
            },
            modifier = modifier
        )
    } else if (uiState is FilterListUIState.Loading) {
        FilterListLoadingScreen(
//            modifier = modifier.fillMaxSize()
        )
    } else {
        FilterListErrorScreen(
            (uiState as FilterListUIState.Error).throwable,
            modifier = modifier,
        )
    }

    /*val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.addFilter(Filter.createRandom())
        }
    }*/
}

@Composable
fun FilterListScreen(
    list: List<Filter>,
    onAddClicked: () -> Unit,
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(50.dp)
        )
    }
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