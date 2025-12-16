package com.github.aakumykov.compose_playground.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBook
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookData
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookMetadata

@Dao
abstract class BookDAO {

    @Transaction @Insert
    fun addBook(book: RoomBook) {
        // BookMetadata должен добавляться первым, чтобы сработал внешний ключ
        addBookMetadata(book.bookMetadata as RoomBookMetadata)
        addBookData(book.bookData as RoomBookData)
    }

    @Insert
    abstract fun addBookData(bookData: RoomBookData)

    @Insert
    abstract fun addBookMetadata(bookMetadata: RoomBookMetadata)


}
