package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.entity.Book
import com.github.aakumykov.compose_playground.repository.room.dao.BookDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class BooksRepository (
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val bookDAO: BookDAO,
) {
    fun addBook(book: Book) {
        bookDAO.addBook(book)
    }
}