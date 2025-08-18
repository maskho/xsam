package dev.vvoc.xsam.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.vvoc.xsam.book.data.database.DatabaseFactory
import dev.vvoc.xsam.book.data.database.FavoriteBookDatabase
import dev.vvoc.xsam.book.data.network.KtorRemoteBookDataSource
import dev.vvoc.xsam.book.data.network.RemoteBookDataSource
import dev.vvoc.xsam.book.data.repository.DefaultBookRepository
import dev.vvoc.xsam.book.domain.BookRepository
import dev.vvoc.xsam.book.presentation.SelectedBookViewModel
import dev.vvoc.xsam.book.presentation.book_detail.BookDetailViewModel
import dev.vvoc.xsam.book.presentation.book_list.BookListViewModel
import dev.vvoc.xsam.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}