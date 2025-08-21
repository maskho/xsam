import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.vvoc.xsam.app.App
import dev.vvoc.xsam.di.initKoin

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream

fun main() {
    initKoin()

    // Inisialisasi Firebase untuk JVM/Desktop
    try {
        val serviceAccount = FileInputStream("composeApp/google-services.json")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    } catch (e: Exception) {
        println("Firebase initialization failed: ${e.message}")
        return // Keluar jika inisialisasi gagal
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "xsam",
        ) { App() }
    }
}