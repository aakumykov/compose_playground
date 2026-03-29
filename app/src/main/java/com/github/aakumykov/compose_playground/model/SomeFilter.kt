package com.github.aakumykov.compose_playground.model

import androidx.room.Embedded

class SomeFilter(
    @Embedded
    val filterMetadata: FilterMetadata,

    ): TheFilter by filterMetadata {
    companion object {
        fun fromFilter(filterMetadata: FilterMetadata): SomeFilter = SomeFilter(
            filterMetadata = filterMetadata
        )
    }
}

