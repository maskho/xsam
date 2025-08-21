package dev.vvoc.xsam.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.vvoc.xsam.book.domain.Book
import dev.vvoc.xsam.book.presentation.book_list.components.BookList
import dev.vvoc.xsam.book.presentation.book_list.components.BookSearchBar
import dev.vvoc.xsam.core.presentation.DarkBlue
import dev.vvoc.xsam.core.presentation.DesertWhite
import dev.vvoc.xsam.core.presentation.SandYellow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import xsam.composeapp.generated.resources.Res
import xsam.composeapp.generated.resources.favorites
import xsam.composeapp.generated.resources.no_favorite_books
import xsam.composeapp.generated.resources.no_search_results
import xsam.composeapp.generated.resources.search_results

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    isLoggedIn: Boolean,
    onBookClick: (Book) -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    BookListScreen(
        state = state,
        isLoggedIn = isLoggedIn,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> onBookClick(action.book)
                is BookListAction.OnLoginClick -> onLoginClick()
                is BookListAction.OnLogoutClick -> {
                    coroutineScope.launch {
                        Firebase.auth.signOut()
                        onLogoutClick()
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun BookListScreen(
    state: BookListState,
    isLoggedIn: Boolean,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔎 Search bar + 👤 account button
        Row(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onAction(BookListAction.OnSearchQueryChange(it))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier.weight(1f)
            )

            var expanded by remember { mutableStateOf(false) }
            val user = Firebase.auth.currentUser

            Box {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User Account",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    if (isLoggedIn && user != null) {
                        // UID + Email
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text("Email: ${user.email ?: "-"}", style = MaterialTheme.typography.bodySmall)
                            Text("UID: ${user.uid}", style = MaterialTheme.typography.bodySmall)
                        }
                        Divider()

                        // Logout
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                                onAction(BookListAction.OnLogoutClick)
                            }
                        )
                    } else {
                        // Login
                        DropdownMenuItem(
                            text = { Text("Login") },
                            onClick = {
                                expanded = false
                                onAction(BookListAction.OnLoginClick)
                            }
                        )
                    }
                }
            }
        }

        // 📚 Tabs + Book lists
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                books = state.searchResults,
                                                onBookClick = {
                                                    onAction(BookListAction.OnBookClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultsListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteBooks.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                } else {
                                    BookList(
                                        books = state.favoriteBooks,
                                        onBookClick = {
                                            onAction(BookListAction.OnBookClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteBooksListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
