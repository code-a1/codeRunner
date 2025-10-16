package com.codea1.coderunner

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.codea1.coderunner.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CodeRunner",
    ) {
        App()
    }
}