package com.codea1.coderunner.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun clearButton(modifier: Modifier = Modifier, enabled: Boolean, onClearButtonClicked: () -> Unit) {
    Button(
        modifier = modifier.padding(8.dp).width(115.dp),
        onClick = onClearButtonClicked,
        colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.tertiary),
        enabled = enabled
    ) {
        Row {
            Icon(
                imageVector = Icons.Filled.CleaningServices,
                contentDescription = "Button to clear the output",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text("Clear")
        }
    }
}

@Preview
@Composable
fun ClearButtonPreviewEnabled() {
    clearButton(
        onClearButtonClicked = {},
        enabled = true
    )
}

@Preview
@Composable
fun ClearButtonPreviewDisabled() {
    clearButton(
        onClearButtonClicked = {},
        enabled = false
    )
}