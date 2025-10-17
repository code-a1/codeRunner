package com.codea1.coderunner.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RunCodeButton(isRunning: State<Boolean>, onRunButtonClicked: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(8.dp).width(115.dp),
        onClick = onRunButtonClicked,
    ) {
        Row {
            if (isRunning.value) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Button to run the code",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text("Stop")
            } else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Button to run the code",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text("Run")
            }
        }
    }
}