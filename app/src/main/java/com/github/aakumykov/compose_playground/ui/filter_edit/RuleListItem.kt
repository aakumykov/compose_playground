package com.github.aakumykov.compose_playground.ui.filter_edit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.model.Rule
import com.github.aakumykov.compose_playground.model.toHumanName
import com.github.aakumykov.compose_playground.model.toSymbols
import com.github.aakumykov.compose_playground.utils.newRandomId

@Composable
fun RuleListItem(
    rule: Rule,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 4.dp,
    onClick: (rule:Rule) -> Unit
) {
    Row(
//        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        /*Text(
            rule.ruleSubject.toHumanName(LocalResources.current),
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .clickable { onClick.invoke(rule) }
        )
        Text(
//            rule.ruleOperation.toSymbols(),
            rule.ruleOperation.toHumanName(LocalResources.current),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(vertical = verticalPadding, horizontal = 6.dp)
                .clickable { onClick.invoke(rule) }
        )
        Text(
            stringResource(R.string.aquotes, rule.checkPattern),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .clickable { onClick.invoke(rule) }
        )*/

        Text(
            stringResource(
                R.string.rule_list_item_title,
                rule.ruleSubject.toHumanName(LocalResources.current),
                rule.ruleOperation.toHumanName(LocalResources.current),
                stringResource(R.string.aquotes, rule.checkPattern),
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding)
                .clickable { onClick.invoke(rule) }
        )
    }
}


@Preview(
    device = "spec:width=500px,height=600px,dpi=240",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
fun RuleListItemDayPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .background(Color.Cyan)
            ,
        horizontalArrangement = Arrangement.Center
    ) {
        RuleListItem(
            rule = Rule.random(newRandomId),
            onClick = {},
        )
    }
}

