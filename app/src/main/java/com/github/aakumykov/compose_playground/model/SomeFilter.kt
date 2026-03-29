package com.github.aakumykov.compose_playground.model

import androidx.room.Embedded

class SomeFilter(
    @Embedded
    val filter: Filter,

): TheFilter by filter {
    companion object {
        fun fromFilter(filter: Filter): SomeFilter = SomeFilter(
            filter = filter
        )
    }
}

