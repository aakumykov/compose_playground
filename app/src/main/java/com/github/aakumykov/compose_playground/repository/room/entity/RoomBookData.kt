package com.github.aakumykov.compose_playground.repository.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.github.aakumykov.compose_playground.entity.BookData

@Entity(
    tableName = "book_data",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = RoomBookMetadata::class,
            parentColumns = ["id"],
            childColumns = ["parentId"]
        )
    ]
)
class RoomBookData(
    val id: String,
    val parentId: String,
    val text: String,
) : BookData {
}