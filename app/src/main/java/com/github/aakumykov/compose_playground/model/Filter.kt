package com.github.aakumykov.compose_playground.model

import androidx.room.Embedded
import androidx.room.Relation

class Filter(
    @Embedded
    val filterMetadata: FilterMetadata,

    @Relation(
        entity = Rule::class,
        parentColumn = "id",
        entityColumn = "filter_id"
    )
    val rules: List<Rule>,

    ): TheFilter by filterMetadata {
    companion object {
        fun create(filterMetadata: FilterMetadata): Filter = Filter(
            filterMetadata = filterMetadata,
            rules = emptyList()
        )

        fun random(): Filter {
            val filterMetadata = FilterMetadata.random
            return Filter(
                filterMetadata = filterMetadata,
                rules = Rule.randomList(filterMetadata.id)
            )
        }
    }
}

