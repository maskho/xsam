package dev.vvoc.xsam

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.vvoc.xsam.app.App
import dev.vvoc.xsam.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "xsam",
        ) { App() }
    }
}