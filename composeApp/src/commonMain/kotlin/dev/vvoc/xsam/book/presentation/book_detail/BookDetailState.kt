package dev.vvoc.xsam.book.presentation.book_detail

import dev.vvoc.xsam.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)