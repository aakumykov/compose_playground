package com.github.aakumykov.compose_playground.entity

import androidx.room.Embedded

class Book(

    @Embedded
    val bookMetadata: BookMetadata,

    val bookData: BookData,

): BookMetadata by bookMetadata, BookData by bookData