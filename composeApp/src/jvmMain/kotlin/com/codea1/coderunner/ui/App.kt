package com.codea1.coderunner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight().background(MaterialTheme.colorScheme.background),
            ) {
                CodeInput(
                    textState = codeInputState,
                    onValueChange = {
                        appViewModel.updateCodeInputState(it)
                    }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight().wrapContentWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                RunCodeButton(
                    isRunning = appViewModel.isRunning.collectAsState(),
                    onRunButtonClicked = {
                        appViewModel.onButtonClick()
                    },
                )
                clearButton(
                    enabled = !appViewModel.isCleanButtonEnabled.collectAsState().value,
                    onClearButtonClicked = {
                        appViewModel.clearOutputAndError()
                    }
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