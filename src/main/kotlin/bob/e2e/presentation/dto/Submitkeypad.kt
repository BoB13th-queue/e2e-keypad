package bob.e2e.presentation.dto

data class Submitkeypad(
    val keypadId: String,
    val timeStamp: String,
    val input: String,
    val hash: String
)
