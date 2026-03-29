package com.github.aakumykov.compose_playground.room.di

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.aakumykov.compose_playground.model.Rule
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDAO {

    @Insert
    fun add(rule: Rule)

    @Update
    fun update(rule: Rule)

    @Query("SELECT * FROM ${Rule.TABLE_NAME} " +
            "WHERE id = :id")
    fun get(id: String): Rule?

    @Query("SELECT * FROM ${Rule.TABLE_NAME} " +
            "WHERE filter_id = :filterId " +
            "ORDER BY created ASC")
    fun listAsFlow(filterId: String): Flow<List<Rule>>
}