package com.github.aakumykov.compose_playground.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.github.aakumykov.compose_playground.entity.Book
import com.github.aakumykov.compose_playground.entity.BookData
import com.github.aakumykov.compose_playground.entity.BookMetadata

@Dao
abstract class BookDAO {

    @Transaction
    fun addBook(book: Book) {

    }

    @Insert
    abstract fun addBookData(bookData: BookData)

    @Insert
    abstract fun addBookMetadata(bookMetadata: BookMetadata)


}
