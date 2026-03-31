package com.github.aakumykov.compose_playground.ui.filter_edit

class NoSuchFilterException(val filterId: String?) : Exception() {
    override val message: String
        get() = "Where is no filterMetadata with id='$filterId'"
}