package com.github.aakumykov.compose_playground.model

interface TheFilter {
    val id: String
    val packageName: String
    val mode: FilterMode
    val enabled: Boolean
    val modified: Long
}