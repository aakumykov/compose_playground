package com.github.aakumykov.compose_playground.model

import androidx.room.Embedded

class Filter(
    @Embedded
    val filterMetadata: FilterMetadata,

    ): TheFilter by filterMetadata {
    companion object {
        fun fromFilter(filterMetadata: FilterMetadata): Filter = Filter(
            filterMetadata = filterMetadata
        )
    }
}

