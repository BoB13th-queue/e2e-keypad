package bob.e2e.presentation.dto

import bob.e2e.domain.model.Keypad

data class GetKeypadResDto(
    val keypadId: String,
    val pubKey: String,
    val timeStamp: String,
    val keypadImage: String,
    val keyData: List<String>,
    val hash: String
)