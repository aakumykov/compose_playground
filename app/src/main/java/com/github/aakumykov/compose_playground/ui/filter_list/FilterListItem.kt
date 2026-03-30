package com.github.aakumykov.compose_playground.ui.filter_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.visible
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.isBlack

@Composable
fun FilterListItem(
    filter: Filter,
    onItemClicked: (filterId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
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
            modifier = Modifier.Companion
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
            modifier = Modifier.Companion
//                .background(Color.LightGray)
                .visible(filter.enabled)
        )
    }
}