package com.github.aakumykov.compose_playground.ui.filters

import androidx.lifecycle.ViewModel
import com.github.aakumykov.compose_playground.repository.FilterRepository
import jakarta.inject.Inject

class FiltersViewModel @Inject constructor(
    private val filterRepository: FilterRepository
): ViewModel() {
}