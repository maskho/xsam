package dev.vvoc.xsam.book.presentation.book_list

import dev.vvoc.xsam.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction
    data object OnLoginClick : BookListAction
    data object OnLogoutClick : BookListAction
}