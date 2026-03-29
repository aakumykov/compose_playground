package com.github.aakumykov.compose_playground.room.di

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.aakumykov.compose_playground.model.Rule

@Dao
interface RuleDAO {

    @Insert
    fun add(rule: Rule)

    @Update
    fun update(rule: Rule)

    @Query("SELECT * FROM ${Rule.TABLE_NAME} " +
            "WHERE filter_id = :filterId " +
            "ORDER BY created ASC")
    fun list(filterId: String): List<Rule>

    @Delete
    fun delete(id: String)
}