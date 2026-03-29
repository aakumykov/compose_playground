package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Query
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.FilterMetadata
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDAO {
    @Query("SELECT * FROM ${FilterMetadata.Companion.TABLE_NAME} ORDER BY modified ASC")
    fun list(): Flow<List<Filter>>

    @Query("SELECT * FROM ${FilterMetadata.Companion.TABLE_NAME} WHERE id = :filterId")
    fun get(filterId: String?): Filter?
}