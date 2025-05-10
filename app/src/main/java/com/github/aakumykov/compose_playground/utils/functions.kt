package com.github.aakumykov.compose_playground.utils

import com.github.javafaker.Book
import com.github.javafaker.Faker
import java.util.Locale
import java.util.UUID

val randomId: String
    get() = UUID.randomUUID().toString()

typealias FakeBook = Book

val faker by lazy { Faker(Locale("ru")) }

val fakeBook: FakeBook
    get() = faker.book()