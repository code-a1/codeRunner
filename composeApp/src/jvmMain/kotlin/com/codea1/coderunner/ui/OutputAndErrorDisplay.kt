package com.codea1.coderunner.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun OutputAndErrorDisplay(runResult: String, runError: AnnotatedString, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
    ) {
        if (runResult.isNotEmpty()) {
            item {
                Text(
                    text = runResult,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        if (runError.isNotEmpty()) {
            item {
                Text(
                    text = runError,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Preview
@Composable
fun OutputAndErrorDisplayPreviewOnError() {
    OutputAndErrorDisplay(
        runResult = "",
        runError = buildAnnotatedString { append("Error: Something went wrong!") }
    )
}

@Preview
@Composable
fun OutputAndErrorDisplayPreviewOnResult() {
    OutputAndErrorDisplay(
        runResult = "Output: Hello, World!",
        runError = buildAnnotatedString { append("") }
    )
}

@Preview
@Composable
fun OutputAndErrorDisplayPreviewOnBoth() {
    OutputAndErrorDisplay(
        runResult = "Output: Hello, World!",
        runError = buildAnnotatedString { append("Error: Something went wrong!") }
    )
}

@Preview
@Composable
fun OutputAndErrorDisplayClickableError() {
    OutputAndErrorDisplay(
        runResult = "",
        runError = RunErrorStringAnnotation.getAnnotatedString("script:2:1: error: cannot find 'foo' in scope") { _, _ -> }
    )
}