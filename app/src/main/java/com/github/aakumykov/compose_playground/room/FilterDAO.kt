package com.github.aakumykov.compose_playground.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.aakumykov.compose_playground.data.model.Filter
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDAO {

    @Insert
    fun add(filter: Filter)

    @Query("SELECT * FROM filters ORDER BY modified ASC")
    fun list(): Flow<List<Filter>>

    @Query("SELECT * FROM filters")
    fun listAsFlow(): Flow<List<Filter>>

    @Query("DELETE FROM filters")
    fun deleteAll()
}
