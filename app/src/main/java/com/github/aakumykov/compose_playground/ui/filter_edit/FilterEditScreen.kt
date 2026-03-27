package com.github.aakumykov.compose_playground.ui.filter_edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun FilterEditScreen(
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterEditViewModel = hiltViewModel()
) {

}