package bob.e2e.presentation.dto

import bob.e2e.domain.model.Keypad

data class GetKeypadResDto(
    val keypadId: Int,
    val pubKey: String,
    val keypadImage: String,
    val keyData: List<String>,
    val hash: String
)