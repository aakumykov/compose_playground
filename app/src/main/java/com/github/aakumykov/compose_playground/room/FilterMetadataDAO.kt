package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.aakumykov.compose_playground.model.FilterMetadata

@Dao
interface FilterMetadataDAO {

    @Insert
    fun add(filterMetadata: FilterMetadata)

    @Update
    fun update(filterMetadata: FilterMetadata)

    @Query("DELETE FROM ${FilterMetadata.TABLE_NAME}")
    fun deleteAll()

    @Query("DELETE FROM ${FilterMetadata.TABLE_NAME} WHERE id = :filterId")
    fun delete(filterId: String)
}

