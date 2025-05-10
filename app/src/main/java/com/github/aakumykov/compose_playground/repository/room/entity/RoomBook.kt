package com.github.aakumykov.compose_playground.repository.room.entity

import com.github.aakumykov.compose_playground.entity.Book

class RoomBook private constructor() {
    companion object {
        fun create(): Book = RoomBookMetadata.create().let {
                Book(
                    bookMetadata = it,
                    bookData = RoomBookData.create(it.id)
                )
            }
    }
}