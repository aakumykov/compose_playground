package com.github.aakumykov.compose_playground.repository.room.entity

import androidx.room.Entity
import com.github.aakumykov.compose_playground.utils.fakeBook
import com.github.aakumykov.compose_playground.utils.randomId

@Entity(
    tableName = "book_metadata",
    primaryKeys = ["id"]
)
class RoomBookMetadata(
    val id: String,
    val title: String,
) {
    companion object {
        fun create(): RoomBookMetadata = RoomBookMetadata(
            id = randomId,
            title = fakeBook.title(),
        )
    }
}