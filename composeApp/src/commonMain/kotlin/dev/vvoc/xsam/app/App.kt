package dev.vvoc.xsam.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.vvoc.xsam.auth.presentation.*
import dev.vvoc.xsam.book.presentation.SelectedBookViewModel
import dev.vvoc.xsam.book.presentation.book_detail.BookDetailAction
import dev.vvoc.xsam.book.presentation.book_detail.BookDetailScreenRoot
import dev.vvoc.xsam.book.presentation.book_detail.BookDetailViewModel
import dev.vvoc.xsam.book.presentation.book_list.BookListScreenRoot
import dev.vvoc.xsam.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val authViewModel = remember { AuthViewModel() }
        val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()

        // Snackbar host
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Route.BookGraph,
            ) {
                navigation<Route.BookGraph>(
                    startDestination = Route.BookList
                ) {
                    // ===== BookList =====
                    composable<Route.BookList>(
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() }
                    ) {
                        val viewModel = koinViewModel<BookListViewModel>()
                        val selectedBookViewModel =
                            it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                        LaunchedEffect(true) {
                            selectedBookViewModel.onSelectBook(null)
                        }

                        BookListScreenRoot(
                            viewModel = viewModel,
                            isLoggedIn = isLoggedIn,
                            onBookClick = { book ->
                                selectedBookViewModel.onSelectBook(book)
                                navController.navigate(Route.BookDetail(book.id))
                            },
                            onLoginClick = {
                                navController.navigate(Route.Login)
                            },
                            onLogoutClick = {
                                authViewModel.logout()
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("You have been logged out")
                                }
                            }
                        )
                    }

                    // ===== BookDetail =====
                    composable<Route.BookDetail>(
                        enterTransition = { slideInHorizontally { it } },
                        exitTransition = { slideOutHorizontally { it } }
                    ) {
                        val viewModel = koinViewModel<BookDetailViewModel>()
                        val selectedBookViewModel =
                            it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                        val selectedBook by selectedBookViewModel
                            .selectedBook
                            .collectAsStateWithLifecycle()

                        LaunchedEffect(selectedBook) {
                            selectedBook?.let { book ->
                                viewModel.onAction(
                                    BookDetailAction.OnSelectedBookChange(book)
                                )
                            }
                        }

                        BookDetailScreenRoot(
                            viewModel = viewModel,
                            onBackClick = { navController.navigateUp() }
                        )
                    }

                    // ===== Login =====
                    composable<Route.Login> {
                        val coroutineScopeScreen = rememberCoroutineScope()
                        var isLoading by remember { mutableStateOf(false) }
                        val authState by authViewModel.authState.collectAsStateWithLifecycle()
                        val isLoggedInState by authViewModel.isLoggedIn.collectAsStateWithLifecycle()

                        // Success login
                        LaunchedEffect(isLoggedInState) {
                            if (isLoggedInState) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Login successful")
                                }
                                navController.popBackStack()
                            }
                        }

                        // Error login
                        LaunchedEffect(authState?.errorMessage) {
                            authState?.errorMessage?.let { error ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Login failed: $error")
                                }
                            }
                        }

                        LoginScreen(
                            isLoading = isLoading,
                            errorMessage = authState?.errorMessage,
                            onLogin = { email, password ->
                                coroutineScopeScreen.launch {
                                    isLoading = true
                                    authViewModel.signIn(email, password)
                                    isLoading = false
                                }
                            },
                            onNavigateToRegister = {
                                navController.navigate(Route.Register)
                            }
                        )
                    }

                    // ===== Register =====
                    composable<Route.Register> {
                        val coroutineScopeScreen = rememberCoroutineScope()
                        var isLoading by remember { mutableStateOf(false) }

                        val authState by authViewModel.authState.collectAsStateWithLifecycle()
                        val isLoggedInState by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
                        var signUpAttempted by remember { mutableStateOf(false) }

                        LaunchedEffect(authState, signUpAttempted) {
                            val state = authState
                            if (signUpAttempted && state != null) {
                                if (state.errorMessage.isNullOrEmpty()) {
                                    if (isLoggedInState) {
                                        authViewModel.logout()
                                    }
                                    signUpAttempted = false
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registration successful ✅")
                                    }
                                    navController.popBackStack()
                                }
                            }
                        }

                        // Error register
                        LaunchedEffect(authState?.errorMessage, signUpAttempted) {
                            if (signUpAttempted) {
                                authState?.errorMessage?.let { error ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Registration failed: $error")
                                    }
                                }
                            }
                        }

                        RegisterScreen(
                            isLoading = isLoading,
                            errorMessage = authState?.errorMessage,
                            onSignUp = { email, password ->
                                coroutineScopeScreen.launch {
                                    signUpAttempted = true
                                    isLoading = true
                                    authViewModel.signUp(email, password)
                                    isLoading = false
                                }
                            },
                            onNavigateToLogin = {
                                signUpAttempted = false
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
