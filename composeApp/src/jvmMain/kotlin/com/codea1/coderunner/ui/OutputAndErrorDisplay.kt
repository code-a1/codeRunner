package com.codea1.coderunner.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OutputAndErrorDisplay(runResult: String, runError: String, modifier: Modifier = Modifier) {
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