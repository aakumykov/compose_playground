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

        val random get() = Filter(
            id = newRandomId,
            modified = Date().time,
            packageName = faker.app().name(),
            mode = FilterMode.random,
            enabled = randomBool
        )


        fun fakeList(size: Int = 5): List<Filter> = buildList {
            repeat(size) {
                add(Companion.random)
            }
        }


        fun fakeListFlow(size: Int = 5): Flow<List<Filter>> = flow {
            emit(fakeList(size))
        }


        fun create(packageName: String,
                   mode: FilterMode,
                   isEnabled: Boolean
        ): Filter = Filter(
            id = newRandomId,
            packageName = packageName,
            mode = mode,
            enabled = isEnabled,
            modified = currentTimestamp
        )
    }
}

val Filter.isBlack: Boolean get() = FilterMode.BLACK == mode