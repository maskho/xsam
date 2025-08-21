package dev.vvoc.xsam.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object BookGraph : Route

    @Serializable
    data object BookList : Route

    @Serializable
    data class BookDetail(val id: String) : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route
}