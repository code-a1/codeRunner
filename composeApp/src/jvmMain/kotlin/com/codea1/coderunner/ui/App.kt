package com.codea1.coderunner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val appViewModel = viewModel { AppViewModel() }
    val runResult by appViewModel.runResult.collectAsState()
    val runError by appViewModel.runError.collectAsState()
    val codeInputState by appViewModel.codeInputState.collectAsState()
    val isRunning by appViewModel.isRunning.collectAsState()
    val isCleanButtonEnabled by appViewModel.isCleanButtonEnabled.collectAsState()
    val focusRequester = appViewModel.codeInputFocusRequester

    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight().background(MaterialTheme.colorScheme.background),
            ) {
                CodeInput(
                    textFieldValue = codeInputState,
                    onValueChange = {
                        appViewModel.updateCodeInputState(it)
                    },
                    focusRequester = focusRequester,
                    isRunning = isRunning,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight().wrapContentWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                RunCodeButton(
                    isRunning = isRunning,
                    onRunButtonClicked = {
                        appViewModel.onButtonClick()
                    },
                )
                ClearButton(
                    enabled = isCleanButtonEnabled,
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