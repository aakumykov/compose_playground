package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.aakumykov.compose_playground.model.FilterMetadata
import com.github.aakumykov.compose_playground.model.SomeFilter
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDAO {

    @Insert
    fun add(filterMetadata: FilterMetadata)

    @Update
    fun update(filterMetadata: FilterMetadata)

    // TODO: выделить в SomeFilterDAO
    @Query("SELECT * FROM ${FilterMetadata.TABLE_NAME} ORDER BY modified ASC")
    fun list(): Flow<List<SomeFilter>>

    @Query("DELETE FROM ${FilterMetadata.TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${FilterMetadata.TABLE_NAME} WHERE id = :filterId")
    fun get(filterId: String?): SomeFilter?

    @Query("DELETE FROM ${FilterMetadata.TABLE_NAME} WHERE id = :filterId")
    fun delete(filterId: String)
}
