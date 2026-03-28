package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.aakumykov.compose_playground.R
import com.github.aakumykov.compose_playground.exceptions.NoSuchFilterException
import com.github.aakumykov.compose_playground.extensions.errorMsgExtended
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.ui.common.ErrorText
import com.github.aakumykov.compose_playground.ui.common.LoadingThrobber
import kotlinx.coroutines.flow.map

@Composable
fun FilterEditScreen(
    filterId: String?,
    onFilterSaved: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterEditViewModel = hiltViewModel()
) {
//    val uiState: FilterEditUIState by viewModel.getUiStateFor(filterId).collectAsStateWithLifecycle()
//    val uiState: FilterEditUIState by remember { mutableStateOf(viewModel.getFilter(filterId)) }

    /*val filterState: State<Filter?> by produceState(null) {
        value = viewModel.getFilter(filterId)
    }*/

    val uiState: FilterEditUIState by viewModel.getFilterAsStateFlow(filterId).collectAsStateWithLifecycle()

    when(uiState) {

        is FilterEditUIState.Success -> {
            FilterEditForm(
                state = uiState as FilterEditUIState.Success,
                modifier = modifier,
                onSaveClicked = onFilterSaved,
                onCancelClicked = onCancelClicked,
            )
        }

        is FilterEditUIState.Error -> {
            ErrorText(
                text = (uiState as FilterEditUIState.Error).throwable.errorMsgExtended,
                modifier = modifier
            )
        }

        is FilterEditUIState.Loading -> {
            LoadingThrobber(modifier = modifier)
        }
    }
}


@Composable
fun FilterEditForm(
    state: FilterEditUIState.Success,
    modifier: Modifier = Modifier,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(text = state.packageName)
        Text(text = state.mode.name)
        Text(text = state.enabled.toString())
        Button(onClick = onSaveClicked) {
            Text(stringResource(R.string.button_save))
        }
        Button(
            onClick = onCancelClicked,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSecondary,
                containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(stringResource(R.string.button_cancel))
        }
    }
}

/*
@Preview(showSystemUi = true)
@Composable
fun FilterEditScreenPreview() {
    FilterEditForm(
        state = FilterEditUIState.Success(Filter.createRandom()),
        onSaveClicked = {},
        onCancelClicked = {},
    )
}*/
