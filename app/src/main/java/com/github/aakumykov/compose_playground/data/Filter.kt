package com.github.aakumykov.compose_playground.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.aakumykov.compose_playground.utils.faker
import com.github.aakumykov.compose_playground.utils.newRandomId
import com.github.aakumykov.compose_playground.utils.randomBool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

@Entity(
    tableName = "filters"
)
data class Filter(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "package_name") val packageName: String,
    val mode: FilterMode,
    val modified: Long,
    val enabled: Boolean,
) {
    companion object {
        fun createRandom(): Filter = Filter(
            id = newRandomId,
            modified = Date().time,
            packageName = faker.app().name(),
            mode = FilterMode.random,
            enabled = randomBool
        )

        fun fakeList(size: Int = 5): List<Filter> = buildList {
            repeat(size) {
                add(createRandom())
            }
        }

        fun fakeListFlow(size: Int = 5): Flow<List<Filter>>
            = flow { emit(fakeList(size)) }
    }
}