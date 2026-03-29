package com.github.aakumykov.compose_playground.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import com.github.aakumykov.compose_playground.utils.faker
import com.github.aakumykov.compose_playground.utils.newRandomId
import com.github.aakumykov.compose_playground.utils.randomBool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

@Entity(tableName = FilterMetadata.TABLE_NAME)
data class FilterMetadata(
    @PrimaryKey
    override val id: String,

    @ColumnInfo(name = "package_name")
    override val packageName: String,

    override val mode: FilterMode,

    override val modified: Long,

    override val enabled: Boolean,
)
    : TheFilter
{
    companion object {

        const val TABLE_NAME = "filters_metadata"

        val random get() = FilterMetadata(
            id = newRandomId,
            modified = Date().time,
            packageName = faker.app().name(),
            mode = FilterMode.random,
            enabled = randomBool
        )


        fun fakeList(size: Int = 5): List<FilterMetadata> = buildList {
            repeat(size) {
                add(Companion.random)
            }
        }


        fun fakeListFlow(size: Int = 5): Flow<List<FilterMetadata>> = flow {
            emit(fakeList(size))
        }


        fun create(packageName: String,
                   mode: FilterMode,
                   isEnabled: Boolean
        ): FilterMetadata = FilterMetadata(
            id = newRandomId,
            packageName = packageName,
            mode = mode,
            enabled = isEnabled,
            modified = currentTimestamp
        )
    }
}

val FilterMetadata.isBlack: Boolean get() = FilterMode.BLACK == mode