package com.codea1.coderunner.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CodeInput(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        visualTransformation = HighlightKeywordsTransformation(),
        decorationBox = { innerTextField ->
            if (textFieldValue.text.isEmpty()) {
                Text(
                    text = "Write here your Kotlin code",
                    color = Color.Gray
                )
            }
            innerTextField()
        }
    )
}

@Preview
@Composable
fun CodeInputPreview() {
    val sampleCode = "fun main() {println(\"Hello, World!\")}"
    val textFieldValue = TextFieldValue(sampleCode)
    CodeInput(
        textFieldValue = textFieldValue,
        onValueChange = {}
    )
}