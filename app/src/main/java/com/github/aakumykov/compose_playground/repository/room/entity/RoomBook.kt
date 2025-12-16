package com.github.aakumykov.compose_playground.repository.room.entity

import androidx.room.Embedded
import androidx.room.Relation

class RoomBook(

    @Embedded
    val bookMetadata: RoomBookMetadata,

    @Relation(
        entity = RoomBookMetadata::class,
        parentColumn = "",
        entityColumn = ""
    )
    val bookData: RoomBookData
) {


    companion object {
        fun create(): RoomBook = RoomBookMetadata.create().let {
            RoomBook(
                    bookMetadata = it,
                    bookData = RoomBookData.create(it.id)
                )
            }
    }
}