package com.codea1.coderunner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OutputAndErrorDisplay(runResult: String, runError: String){
    Column {
        if (runResult.isNotEmpty()){
            Text(
                text = runResult,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        if (runError.isNotEmpty()){
            Text(
                text = runError,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}