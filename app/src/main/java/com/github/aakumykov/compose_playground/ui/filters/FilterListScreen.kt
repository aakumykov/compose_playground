package com.github.aakumykov.compose_playground.ui.filters

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.data.model.Filter
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.model.FilterListUIState

@Composable
fun FilterListScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterListViewModel = hiltViewModel(),
) {
    val uiState: FilterListUIState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is FilterListUIState.Success -> FilterListScreen(
            (uiState as FilterListUIState.Success).list,
            onAddClicked = {},
            modifier = modifier
        )
        is FilterListUIState.Loading -> FilterListLoadingScreen(
            modifier = modifier
        )
        else -> FilterListErrorScreen(
            (uiState as FilterListUIState.Error).throwable,
            modifier = modifier,
        )
    }
}

@Composable
fun FilterListScreen(
    list: List<Filter>,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(items = list, key = { it.id }) { filter ->
            Text(
                text = filter.packageName,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}


@Composable
fun FilterListLoadingScreen(modifier: Modifier = Modifier){
//    CircularProgressIndicator(
//        modifier = modifier.fillMaxWidth()
//    )
    /*LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(5.dp)
    )*/
    Text(
        text = "Ожидание...",
        modifier = modifier.fillMaxWidth()
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