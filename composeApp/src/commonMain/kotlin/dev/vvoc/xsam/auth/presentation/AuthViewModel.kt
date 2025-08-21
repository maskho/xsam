package dev.vvoc.xsam.auth.presentation

import dev.vvoc.xsam.auth.model.*
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.vvoc.xsam.auth.data.FirebaseAuthRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AuthViewModel {

    private val repo = FirebaseAuthRepository()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> get() = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(Firebase.auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn.asStateFlow()

    init {
        scope.launch {
            Firebase.auth.authStateChanged.collect { user ->
                if (user != null) {
                    _isLoggedIn.value = true
                    if (_authState.value == null || _authState.value?.success == false) {
                        _authState.value = AuthResponse(
                            success = true,
                            uid = user.uid,
                            email = user.email
                        )
                    }
                } else {
                    _isLoggedIn.value = false
                    _authState.value = null
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        scope.launch {
            try {
                val result = repo.signUp(SignUpRequest(email, password))
                _authState.value = result
                _isLoggedIn.value = result.success
            } catch (e: Exception) {
                _authState.value = AuthResponse(success = false, errorMessage = e.message)
                _isLoggedIn.value = false
            }
        }
    }

    fun signIn(email: String, password: String) {
        scope.launch {
            try {
                val result = repo.signIn(SignInRequest(email, password))
                _authState.value = result
                _isLoggedIn.value = result.success
            } catch (e: Exception) {
                _authState.value = AuthResponse(success = false, errorMessage = e.message)
                _isLoggedIn.value = false
            }
        }
    }

    fun logout() {
        scope.launch {
            try {
                repo.signOut()
                _authState.value = null
                _isLoggedIn.value = false
            } catch (e: Exception) {
                _authState.value = AuthResponse(success = false, errorMessage = e.message)
            }
        }
    }
}
