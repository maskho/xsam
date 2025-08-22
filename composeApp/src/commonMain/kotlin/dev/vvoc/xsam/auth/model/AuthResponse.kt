package dev.vvoc.xsam.auth.model

data class AuthResponse(
    val success: Boolean,
    val uid: String? = null,
    val email: String? = null,
    val error: FirebaseError? = null,
    val errorMessage: String? = null
)

data class FirebaseError(
    val message: String? = null
)
