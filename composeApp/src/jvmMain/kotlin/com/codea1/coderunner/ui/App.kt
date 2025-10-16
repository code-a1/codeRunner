package com.codea1.coderunner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val appViewModel = AppViewModel()
    val runResult by appViewModel.runResult.collectAsState()
    val runError by appViewModel.runError.collectAsState()
    val codeInputState = appViewModel.codeInputState.collectAsState()
        MaterialTheme {
        Row (
            modifier = Modifier.fillMaxSize(),
        ) {
            Box (
                modifier = Modifier.weight(1f).fillMaxHeight().background(MaterialTheme.colorScheme.background),
            ) {
                CodeInput(
                    textState = codeInputState,
                    onValueChange = {
                        appViewModel.updateCodeInputState(it)
                    }
                )
            }
            Box (
                modifier = Modifier.fillMaxHeight().wrapContentWidth().background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                RunCodeButton(
                    isRunning = appViewModel.isRunning.collectAsState(),
                    onRunButtonClicked = {
                    appViewModel.onButtonClick()
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight().background(MaterialTheme.colorScheme.background)
            ) {
                OutputAndErrorDisplay(runResult, runError)
            }
        }
    }
}