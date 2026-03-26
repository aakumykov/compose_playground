package com.github.aakumykov.compose_playground.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "filters"
)
data class Filter(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "package_name") val packageName: String,
    val mode: FilterMode,
    val modified: Long,
    val enabled: Boolean,
)