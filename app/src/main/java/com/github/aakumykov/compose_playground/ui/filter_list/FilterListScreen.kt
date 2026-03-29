package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
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
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.isBlack
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
fun FilterList(
    list: List<Filter>,
    onItemClicked: (filterId: String) -> Unit,
    onAddClicked: () -> Unit,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(items = list, key = { it.id }) { filter ->
                FilterListItem(filter, onItemClicked)
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

@Composable
fun FilterListItem(
    filter: Filter,
    onItemClicked: (filterId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (filter.filterMetadata.isBlack) Icons.Default.Circle
            else Icons.Outlined.Circle,
            contentDescription = stringResource(
                if (filter.filterMetadata.isBlack) R.string.description_filter_list_item_mode_icon_black
                else R.string.description_filter_list_item_mode_icon_white
            )
        )
        Text(
            text = filter.packageName,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1.0f, true)
//                .background(Color.Cyan)
                .padding(
                    start = 14.dp,
                    end = 0.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
                .clickable {
                    onItemClicked.invoke(filter.id)
                }
        )
        Icon(
            Icons.Default.Check,
            contentDescription = stringResource(R.string.description_filter_list_item_enabled),
            modifier = Modifier
//                .background(Color.LightGray)
                .visible(filter.enabled)
        )
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