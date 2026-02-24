package com.github.aakumykov.compose_playground

import com.github.aakumykov.compose_playground.utils.fakeName
import com.github.aakumykov.compose_playground.utils.random

data class Person(
    val name: String,
    val age: Int
) {
    companion object {
        fun random(): Person = Person(
            name = fakeName,
            age = random.nextInt(0,111)
        )
        fun randomList(size: Int = 5): List<Person> = buildList { repeat(size) {
            add(Person.random())
        } }
    }
}