package com.codea1.coderunner.ui

import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration

class RunErrorStringAnnotation {
    companion object {
        private val regex = """[^\s(]+:(?<line>\d+)(:(?<position>\d+):|\))""".toRegex()

        fun getAnnotatedString(runError: String, onErrorClick: (Int, Int?) -> Unit) = buildAnnotatedString {
            append(runError)
            regex.findAll(runError).forEach { match ->
                val line = match.groups["line"]?.value?.toInt()
                val position = match.groups["position"]?.value?.toInt()

                if (line != null) {
                    addLink(
                        LinkAnnotation.Clickable(
                            tag = match.value,
                            linkInteractionListener = LinkInteractionListener {
                                onErrorClick(line, position)
                            },
                            styles = TextLinkStyles(
                                style = SpanStyle(textDecoration = TextDecoration.Underline),
                            )
                        ),
                        match.range.first,
                        match.range.last
                    )
                }

            }
        }
    }
}