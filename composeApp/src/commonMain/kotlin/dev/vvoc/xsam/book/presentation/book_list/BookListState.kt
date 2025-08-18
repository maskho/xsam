package dev.vvoc.xsam.book.presentation.book_list

import dev.vvoc.xsam.book.domain.Book
import dev.vvoc.xsam.core.presentation.UIText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UIText? = null
)