package bob.e2e.presentation.dto

import bob.e2e.domain.model.Keypad

data class GetKeypadResDto(
    val keypadId: Int,
    val keyboardData: List<Pair<String, String>>,
    val hash: String
)