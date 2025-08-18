package dev.vvoc.xsam

import androidx.compose.ui.window.ComposeUIViewController
import dev.vvoc.xsam.app.App
import dev.vvoc.xsam.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }