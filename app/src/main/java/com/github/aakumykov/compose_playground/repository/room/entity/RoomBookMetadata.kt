package com.github.aakumykov.compose_playground.repository.room.entity

import androidx.room.Entity
import com.github.aakumykov.compose_playground.entity.BookMetadata

@Entity(
    tableName = "book_metadata",
    primaryKeys = ["id"]
)
class RoomBookMetadata(
    val id: String,
    val title: String,
) : BookMetadata