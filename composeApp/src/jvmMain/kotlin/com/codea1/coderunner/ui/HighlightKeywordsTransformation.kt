package com.codea1.coderunner.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class HighlightKeywordsTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = buildAnnotatedString {
            append(text.text)
            for (keyword in keywordsColors) {
                var startIndex = text.text.indexOf(keyword.key, ignoreCase = true)
                while (startIndex != -1) {
                    val endIndex = startIndex + keyword.key.length
                    addStyle(
                        style = SpanStyle(
                            color = keyword.value,
                            fontWeight = FontWeight.Bold,
                        ),
                        start = startIndex,
                        end = endIndex
                    )
                    startIndex = text.text.indexOf(keyword.key, startIndex + 1, ignoreCase = true)
                }
            }
        }

        return TransformedText(
            text = annotatedString,
            offsetMapping = OffsetMapping.Identity
        )
    }

    val keywordsColors: Map<String, Color> = mapOf(
        "if" to Color(0xFF22BBA8),
        "else" to Color(0xFF22BBA8),
        "when" to Color(0xFF22BBA8),
        "val" to Color(0xFF2247B3),
        "var" to Color(0xFF2247B3),
        "while" to Color(0xFFBD258B),
        "for" to Color(0xFFBD258B),
        "fun" to Color(0xFFBDBF25),
        "break" to Color(0xFF34D429),
        "null" to Color(0xFF00D3E7),
    )
}