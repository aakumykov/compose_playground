package com.github.aakumykov.compose_playground.exceptions

class NoSuchFilterException(val filterId: String?) : Exception() {
    override val message: String
        get() = "Where is no filter with id='$filterId'"
}