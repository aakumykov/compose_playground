package com.github.aakumykov.compose_playground.repository

import com.github.aakumykov.compose_playground.entity.Book
import com.github.aakumykov.compose_playground.repository.room.dao.BookDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BooksRepository (
    private val bookDAO: BookDAO,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun addBook(book: Book) {
        withContext(coroutineDispatcher) {
            bookDAO.addBook(book)
        }
    }
}