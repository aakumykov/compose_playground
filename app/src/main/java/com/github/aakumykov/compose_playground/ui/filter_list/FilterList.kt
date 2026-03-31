package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.Filter

@Composable
fun FilterList(
    list: List<Filter>,
    onItemClicked: (filterId: String) -> Unit,
    onAddClicked: () -> Unit,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            stringResource(R.string.page_title_filter_list),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
//            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
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
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = stringResource(R.string.description_filter_add_button)
            )
        }
    }
}


@Preview()
@Composable
fun FilterListPreview() {
    FilterList(
        list = emptyList(),
        onItemClicked = {},
        onAddClicked = {},
        onClearClicked = {},
        modifier = Modifier
            .height(400.dp)
    )
}