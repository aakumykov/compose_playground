package com.github.aakumykov.compose_playground.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.github.aakumykov.compose_playground.entity.Book
import com.github.aakumykov.compose_playground.entity.BookData
import com.github.aakumykov.compose_playground.entity.BookMetadata
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookData
import com.github.aakumykov.compose_playground.repository.room.entity.RoomBookMetadata

@Dao
abstract class BookDAO {

//    @Transaction
    fun addBook(book: Book) {
        addBookData(book.bookData as RoomBookData)
        addBookMetadata(book.bookMetadata as RoomBookMetadata)
    }

    @Insert
    abstract fun addBookData(bookData: RoomBookData)

    @Insert
    abstract fun addBookMetadata(bookMetadata: RoomBookMetadata)


}
