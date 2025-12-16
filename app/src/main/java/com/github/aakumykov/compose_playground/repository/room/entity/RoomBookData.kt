package com.github.aakumykov.compose_playground.repository.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.github.aakumykov.compose_playground.utils.randomId

@Entity(
    tableName = "book_data",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            deferred = true,
            entity = RoomBookMetadata::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["parentId"])
    ]
)
class RoomBookData(
    val id: String,
    val parentId: String,
    val text: String,
) {
    companion object {
        fun create(metadataId: String) = RoomBookData(
            id = randomId,
            parentId = metadataId,
            text = "Текст книги"
        )
    }
}