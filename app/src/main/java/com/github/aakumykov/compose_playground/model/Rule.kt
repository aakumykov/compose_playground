package com.github.aakumykov.compose_playground.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.github.aakumykov.compose_playground.utils.currentTimestamp
import com.github.aakumykov.compose_playground.utils.newRandomId
import com.github.aakumykov.compose_playground.utils.randomString

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
    @ColumnInfo(defaultValue = "0") val created: Long
) {
    companion object {
        const val TABLE_NAME = "rules"

        fun random(filterId: String): Rule = Rule(
            id = newRandomId,
            filterId = filterId,
            ruleSubject = RuleSubject.entries.random(),
            ruleOperation = RuleOperation.entries.random(),
            checkPattern = randomString,
            created = currentTimestamp
        )

        fun randomList(filterId: String, size: Int = 3): List<Rule> = buildList { repeat(size) {
            add(random(filterId))
        } }
    }
}