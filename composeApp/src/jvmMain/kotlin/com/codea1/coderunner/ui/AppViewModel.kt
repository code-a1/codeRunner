package com.codea1.coderunner.ui

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class AppViewModel: ViewModel() {

    private val _codeInputState = MutableStateFlow(TextFieldValue())
    val codeInputState = _codeInputState

    private var _runResult: MutableStateFlow<String> = MutableStateFlow("Run result will be displayed here.")
    val runResult: StateFlow<String> = _runResult

    private var _runError: MutableStateFlow<AnnotatedString> = MutableStateFlow(buildAnnotatedString { append("") })
    val runError: StateFlow<AnnotatedString> = _runError

    var _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    var _isCleanButtonEnabled = MutableStateFlow(false)
    val isCleanButtonEnabled: StateFlow<Boolean> = _isCleanButtonEnabled

    val codeInputFocusRequester = FocusRequester()

    private var currentJob: Job? = null
        set(value) {
            field = value
            _isRunning.value = value != null
            _isCleanButtonEnabled.value = value == null
        }

    private fun clearRunOutput() {
        _runResult.value = ""
    }

    private fun clearRunError() {
        _runError.value = buildAnnotatedString { append("") }
    }

    fun clearOutputAndError() {
        _runResult.value = "Run result will be displayed here."
        clearRunError()
        _isCleanButtonEnabled.value = false
    }

    fun updateCodeInputState(newValue: TextFieldValue) {
        _codeInputState.value = newValue
    }

    fun onButtonClick() {
        if (currentJob != null) {
            currentJob?.cancel()
        } else {
            runCode()
        }
    }

    fun setCurrentSelection(line: Int, position: Int?) {
        var index = 0
        val lines = _codeInputState.value.text.lines()
        for (i in 0..<line - 1) {
            index += if (lines[i].isNotEmpty()) lines[i].length + 1 else 1
        }

        val selection = when (position) {
            null -> TextRange(start = index, end = index + lines[line - 1].length)
            else -> TextRange(index = index + position - 1)
        }

        _codeInputState.value =
            _codeInputState.value.copy(selection = selection)

        codeInputFocusRequester.requestFocus()
    }

    fun runCode() {
        File("script.kts").writeText(codeInputState.value.text)

        currentJob = CoroutineScope(Dispatchers.IO).launch {
            val command = when (System.getProperty("os.name").lowercase().contains("win")) {
                true -> listOf("cmd", "/c", "kotlinc", "-script", "script.kts")
                false -> listOf("kotlinc", "-script", "script.kts")
            }
            val processBuilder = ProcessBuilder(command)

            val currentProcess = processBuilder.start()

            val outputBuffer = currentProcess.inputStream.bufferedReader()
            val errorBuffer = currentProcess.errorStream.bufferedReader()

            clearRunOutput()
            clearRunError()

            try {
                while (true) {
                    ensureActive()
                    val line = async { outputBuffer.readLine() }.await() ?: break
                    ensureActive()
                    _runResult.value += "$line\n"
                }

                while (true) {
                    ensureActive()
                    val line = async { errorBuffer.readLine() }.await() ?: break
                    ensureActive()
                    _runError.value = buildAnnotatedString {
                        append(_runError.value)
                        append(
                            RunErrorStringAnnotation.getAnnotatedString(
                                if (_runError.value.isEmpty()) "Exit code: ${currentProcess.exitValue()} \n $line\n" else "$line\n",
                                onErrorClick = { line, position ->
                                    setCurrentSelection(line, position)
                                })
                        )
                    }
                }
            } catch (_: CancellationException) {
                currentProcess.children().forEach { processHandle -> processHandle.destroyForcibly() }
                currentProcess.destroyForcibly()
                currentJob = null
            } finally {
                outputBuffer.close()
                errorBuffer.close()
            }

            currentJob = null
        }
    }
}