package dev.vvoc.xsam.book.data.network

import dev.vvoc.xsam.book.data.dto.BookWorkDto
import dev.vvoc.xsam.book.data.dto.SearchResponseDto
import dev.vvoc.xsam.core.domain.DataError
import dev.vvoc.xsam.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}