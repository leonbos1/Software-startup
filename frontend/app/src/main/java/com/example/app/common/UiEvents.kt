package com.example.app.common

sealed class UiEvents {
    data class SnackbarEvent(val message : String) : UiEvents()
    data class NavigateEvent(val message: String) : UiEvents()

}