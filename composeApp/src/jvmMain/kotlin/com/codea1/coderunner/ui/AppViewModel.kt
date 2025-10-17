package com.codea1.coderunner.ui

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class AppViewModel {

    private val _codeInputState = MutableStateFlow(TextFieldValue())
    val codeInputState = _codeInputState

    private var _runResult: MutableStateFlow<String> = MutableStateFlow("Run result will be displayed here.")
    val runResult: StateFlow<String> = _runResult

    private var _runError: MutableStateFlow<String> = MutableStateFlow("")
    val runError: StateFlow<String> = _runError

    var _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    var _isCleanButtonEnabled = MutableStateFlow(false)
    val isCleanButtonEnabled: StateFlow<Boolean> = _isCleanButtonEnabled

    private var currentJob: Job? = null
        set(value) {
            field = value
            _isRunning.value = value != null
            _isCleanButtonEnabled.value = value != null
        }

    private fun clearRunOutput() {
        _runResult.value = ""
    }

    private fun clearRunError() {
        _runError.value = ""
    }

    fun clearOutputAndError() {
        _runResult.value = "Run result will be displayed here."
        clearRunError()
        _isCleanButtonEnabled.value = true
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

            CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    if (!(currentJob?.isActive ?: false)) {
                        currentProcess.destroyForcibly()
                        currentJob = null
                        break
                    }
                }
            }

            while (true) {
                ensureActive()
                val line = outputBuffer.readLine() ?: break
                ensureActive()
                _runResult.value += line + "\n"
            }

            while (true) {
                ensureActive()
                val line = errorBuffer.readLine() ?: break
                ensureActive()
                _runError.value += line + "\n"
            }

            currentJob = null
        }
    }
}