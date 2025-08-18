package dev.vvoc.xsam.book.data.dto

import kotlinx.serialization.Serializable

@Serializable(BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String? = null
)
