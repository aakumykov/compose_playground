package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.aakumykov.compose_playground.model.Filter
import com.github.aakumykov.compose_playground.model.SomeFilter
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDAO {

    @Insert
    fun add(filter: Filter)

    @Update
    fun update(filter: Filter)

    // TODO: выделить в SomeFilterDAO
    @Query("SELECT * FROM filters ORDER BY modified ASC")
    fun list(): Flow<List<SomeFilter>>

    @Query("DELETE FROM filters")
    fun deleteAll()

    @Query("SELECT * FROM filters WHERE id = :filterId")
    fun get(filterId: String?): SomeFilter?

    @Query("DELETE FROM filters WHERE id = :filterId")
    fun delete(filterId: String)
}
