package com.github.aakumykov.compose_playground.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = Rule.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = FilterMetadata::class,
            parentColumns = ["id"],
            childColumns = ["filter_id"],
            onDelete = CASCADE,
        )]
)
class Rule(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "filter_id") val filterId: String,
    @ColumnInfo(name = "rule_subject") val ruleSubject: RuleSubject,
    @ColumnInfo(name = "rule_operation") val ruleOperation: RuleOperation,
    @ColumnInfo(name = "check_pattern") val checkPattern: String,
    val created: Long
) {
    companion object {
        const val TABLE_NAME = "rules"
    }
}