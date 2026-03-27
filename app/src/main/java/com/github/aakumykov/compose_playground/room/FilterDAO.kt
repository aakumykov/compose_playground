package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.aakumykov.compose_playground.model.Filter
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDAO {

    @Insert
    fun add(filter: Filter)

    @Query("SELECT * FROM filters ORDER BY modified ASC")
    fun list(): Flow<List<Filter>>

    @Query("DELETE FROM filters")
    fun deleteAll()

    @Query("SELECT * FROM filters WHERE id = :filterId")
    fun getFilterAsFlow(filterId: String?): Flow<Filter?>
}
