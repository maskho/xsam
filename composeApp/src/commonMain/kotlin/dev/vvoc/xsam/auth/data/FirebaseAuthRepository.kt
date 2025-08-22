package dev.vvoc.xsam.auth.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.vvoc.xsam.auth.model.*

class FirebaseAuthRepository {

    private val auth = Firebase.auth

    suspend fun signUp(request: SignUpRequest): AuthResponse {
        return try {
            val result = auth.createUserWithEmailAndPassword(request.email, request.password)
            val user = result.user
            AuthResponse(
                success = true,
                uid = user?.uid,
                email = user?.email
            )
        } catch (e: Exception) {
            AuthResponse(success = false, errorMessage = e.message ?: "Unknown error during sign up")
        }
    }

    suspend fun signIn(request: SignInRequest): AuthResponse {
        return try {
            val result = auth.signInWithEmailAndPassword(request.email, request.password)
            val user = result.user
            AuthResponse(
                success = true,
                uid = user?.uid,
                email = user?.email
            )
        } catch (e: Exception) {
            AuthResponse(success = false, errorMessage = e.message ?: "Unknown error during sign in")
        }
    }

    suspend fun signOut(): AuthResponse {
        return try {
            auth.signOut()
            AuthResponse(success = true)
        } catch (e: Exception) {
            AuthResponse(success = false, errorMessage = e.message ?: "Unknown error during sign out")
        }
    }

    fun getCurrentUser(): AuthResponse {
        val user = auth.currentUser
        return if (user != null) {
            AuthResponse(
                success = true,
                uid = user.uid,
                email = user.email
            )
        } else {
            AuthResponse(success = false, errorMessage = "No user logged in")
        }
    }
}
